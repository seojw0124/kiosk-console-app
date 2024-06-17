import model.CartInfo
import model.CategoryInfo
import model.OrderInfo
import utils.FormatUtil
import java.time.LocalDateTime
import kotlin.system.exitProcess

class KioskManager {
    private lateinit var categoryManager: CategoryManager
    private lateinit var menuManager: MenuManager
    private lateinit var cartManager: CartManager
    private lateinit var orderManager: OrderManager

    private var money: Int = 0

    fun initKiosk() {
        init()

        println("***** 정우 카페에 오신 것을 환영합니다 *****")

        money = inputMoney()
        runKiosk()
    }

    private fun init() {
        categoryManager = CategoryManager(initCategoryData())
        menuManager = MenuManager(initMenuData())
        cartManager = CartManager()
        orderManager = OrderManager()
    }

    private fun inputMoney(): Int {
        println("소지하고 계신 금액을 입력해주세요.")
        while (true) {
            try {
                val money = readInt()
                if (money > 0) {
                    return money
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                println("소지하고 계신 금액을 다시 입력해주세요")
            }
        }
    }

    private fun runKiosk() {
        while (true) {
            categoryManager.showCategoryList()
            val selectedCategoryId = getSelectedCategoryId()

            when (selectedCategoryId) {
                0 -> break
                9 -> handleCart()
                else -> handleMenu(selectedCategoryId)
            }
        }
    }

    private fun getSelectedCategoryId(): Int {
        while (true) {
            try {
                val categoryId = readInt()
                return when (categoryId) {
                    in 1..categoryManager.getCategoryItemCount() -> categoryId
                    9 -> categoryId
                    0 -> categoryId
                    else -> throw IndexOutOfBoundsException()
                }
            } catch (e: IndexOutOfBoundsException) {
                print("없는 카테고리입니다. 다시 입력해주세요. 카테고리: ")
            }
        }
    }

    private fun handleMenu(categoryId: Int) {
        while (true) {
            menuManager.showDetailMenu(categoryId)
            print("메뉴를 선택하세요(0: 뒤로가기): ")
            val itemId = getSelectedMenuItemId(categoryId)
            if (itemId == 0) break

            val item = menuManager.getMenuItem(categoryId, itemId)
            item?.let {
                it.displayDetailInfo()
                val quantity = getQuantity()
                addMenuItemToCart(it, quantity)
            }
        }
    }

    private fun getSelectedMenuItemId(categoryId: Int): Int {
        while (true) {
            try {
                val itemId = readInt()
                return when (itemId) {
                    in 1..menuManager.getMenuItemCount(categoryId) -> itemId
                    0 -> itemId
                    else -> throw IndexOutOfBoundsException()
                }
            } catch (e: IndexOutOfBoundsException) {
                print("없는 메뉴입니다. 다시 입력해주세요. 메뉴: ")
            }
        }
    }

    private fun addMenuItemToCart(item: MenuItem, quantity: Int) {
        if (money < item.price * quantity) {
            println("\n잔액이 부족합니다. 다른 상품을 선택해주세요. 현재 잔액: ${FormatUtil().decimalFormat(money)}원\n")
            return
        }

        print("장바구니에 담으시겠습니까? (y/n): ")
        while (true) {
            try {
                val answer = readln().lowercase()
                when (answer) {
                    "y" -> {
                        val cartId =
                            if (cartManager.isCartEmpty()) {
                                1
                            } else {
                                cartManager.getLastCartItemId() + 1
                            }
                        val cartInfo = CartInfo(
                            cartId,
                            item.itemId,
                            item.name,
                            item.price,
                            quantity
                        )
                        cartManager.addCartItem(cartInfo, item.categoryId)
                        break
                    }
                    "n" -> {
                        println("장바구니에 담기를 취소했습니다.")
                        break
                    }
                    else -> throw Exception()
                }
            } catch (e: Exception) {
                print("잘못된 입력입니다. 다시 입력해주세요(y/n): ")
            }
        }
    }

    private fun handleCart() {
        if (cartManager.isCartEmpty()) {
            println("\n장바구니가 비어있습니다. 상품을 추가해주세요.\n")
            return
        }

        cartManager.showMyCart(money)

        print("메뉴를 선택하세요: ")
        val selectedCartMenu = readInt()
        when (selectedCartMenu) {
            1 -> orderCartItem()
            0 -> return
            else -> println("없는 번호입니다. 다시 입력해주세요.")
        }
    }

    private fun orderCartItem() {
        if (money < cartManager.getCartItemTotalPrice()) {
            println("\n현재 잔액은 ${FormatUtil().decimalFormat(money)}원으로 ${FormatUtil().decimalFormat(cartManager.getCartItemTotalPrice() - money)}원이 부족해서 결제할 수 없습니다.\n")
            return
        }

        val cartItemList = cartManager.getMyCartItemList()
        val orderId =
            if (orderManager.isOrderListEmpty()) {
                1
            } else {
                orderManager.getLastOrderItemId() + 1
            }

        // 결제 시간은 현재 시간으로 설정(2024-06-14 00:00:00)
        val localDateTime = LocalDateTime.now().toString()
        val orderDate = FormatUtil().getFormattedDate(localDateTime)
        val orderItem = OrderInfo(orderId, cartItemList, orderDate)

        orderManager.addOrderItem(orderItem)

        print("결제가 완료되었습니다. 영수증을 출력하시겠습니까? (y/n): ")
        val answer = readln().lowercase()
        if (answer == "y") {
            orderManager.showReceipt()
            cartManager.clearMyCart()
        } else {
            println("\n주문번호: $orderId\n")
        }

        println("프로그램을 종료합니다.")
        exitProcess(0)
    }

    private fun getQuantity(): Int {
        while (true) {
            print("수량을 입력하세요: ")
            val quantity = readInt()
            if (quantity > 0) {
                return quantity
            } else {
                println("수량은 1 이상이어야 합니다. 다시 입력해주세요.")
            }
        }
    }

    private fun readInt(): Int {
        return readln().toIntOrNull() ?: 0
    }

    /* 데이터 초기화 */
    private fun initCategoryData(): ArrayList<CategoryInfo> {
        return arrayListOf(
            CategoryInfo(1, "음료"),
            CategoryInfo(2, "푸드"),
            CategoryInfo(3, "상품"),
            CategoryInfo(9, "장바구니"),
        )
    }

    private fun initMenuData(): ArrayList<MenuItem> {
        return arrayListOf(
            Drink(1, 1, "아메리카노", 4500),
            Drink(2, 1, "카페라떼", 5500),
            Drink(3, 1, "카푸치노", 5000),
            Drink(4, 1, "바닐라라떼", 6000),
            Food(1, 2, "카스테라", 4500),
            Food(2, 2, "케이크", 5700),
            Food(3, 2, "마카롱", 2700),
            Product(1, 3, "머그컵", 14000),
            Product(2, 3, "텀블러", 25000),
        )
    }
}
