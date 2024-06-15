import model.CartInfo
import model.CategoryInfo
import model.OrderInfo
import utils.FormatUtil
import java.time.LocalDateTime
import kotlin.system.exitProcess

class KioskManager {
    private lateinit var globalManager: GlobalManager
    private var money: Int = 0

    fun initKiosk() {
        initGlobalManager()

        println("***** 정우 카페에 오신 것을 환영합니다 *****")

        money = inputMoney()
        runKiosk()
    }

    private fun initGlobalManager() {
        val categoryManager = CategoryManager(initCategoryData())
        val menuManager = MenuManager(initMenuData())
        val cartManager = CartManager()
        val orderManager = OrderManager()

        globalManager = GlobalManager(categoryManager, menuManager, cartManager, orderManager)
    }

    private fun inputMoney(): Int {
        println("소지하고 계신 금액을 입력해주세요.")
        while (true) {
            try {
                val originMoney = readInt()
                if (originMoney > 0) {
                    return originMoney
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
            globalManager.categoryManager.showCategory()
            val selectedCategoryId = getSelectedCategoryId()

            if (selectedCategoryId == 0) {
                break
            } else if (selectedCategoryId == 8) {
                handleCart()
                continue
            }
            handleMenu(selectedCategoryId)
        }
    }

    private fun getSelectedCategoryId(): Int {
        while (true) {
            try {
                val categoryId = readInt()
                return when (categoryId) {
                    in 1..globalManager.categoryManager.getItemCount() -> categoryId
                    8 -> categoryId
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
            globalManager.menuManager.showDetailMenu(categoryId)
            print("메뉴를 선택하세요(0: 뒤로가기): ")
            val itemId = getSelectedMenuItemId()
            if (itemId == 0) break

            val item = globalManager.menuManager.getMenuItem(categoryId, itemId)
            item?.let {
                it.displayDetailInfo()
                val quantity = getQuantity()
                addItemToCart(it, quantity)
            }
        }
    }

    private fun getSelectedMenuItemId(): Int {
        while (true) {
            try {
                val itemId = readInt()
                return when (itemId) {
                    in 1..globalManager.categoryManager.getItemCount() -> itemId
                    8 -> itemId
                    0 -> itemId
                    else -> throw IndexOutOfBoundsException()
                }
            } catch (e: IndexOutOfBoundsException) {
                print("없는 메뉴입니다. 다시 입력해주세요. 메뉴: ")
            }
        }
    }

    private fun addItemToCart(item: MenuItem, quantity: Int) {
        if (money < item.price * quantity) {
            println("소지금이 부족합니다. 다른 상품을 선택해주세요. 현재 소지금: ${FormatUtil().decimalFormat(money)}원")
            return
        }

        print("장바구니에 담으시겠습니까? (y/n): ")
        while (true) {
            try {
                val answer = readln().lowercase()
                when (answer) {
                    "y" -> {
                        val cartId =
                            if (globalManager.cartManager.isCartEmpty()) {
                                1
                            } else {
                                globalManager.cartManager.getLastCartItemId() + 1
                            }
                        val cartInfo = CartInfo(
                            cartId,
                            item.itemId,
                            item.name,
                            item.price,
                            quantity
                        )
                        globalManager.cartManager.addCartItem(cartInfo, item.categoryId)
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
        globalManager.cartManager.showMyCart(money)

        print("메뉴를 선택하세요: ")
        val selectedCartMenu = readInt()
        when (selectedCartMenu) {
            1 -> orderCartItem()
            0 -> return
            else -> println("없는 번호입니다. 다시 입력해주세요.")
        }
    }

    private fun orderCartItem() {
        val cartItemList = globalManager.cartManager.getMyCartItemList()
        println("cartItemList: $cartItemList")
        val orderId =
            if (globalManager.orderManager.isOrderListEmpty()) {
                1
            } else {
                globalManager.orderManager.getLastOrderItemId() + 1
            }

        // 결제 시간은 현재 시간으로 설정(2024-06-14 00:00:00)
        val localDateTime = LocalDateTime.now().toString()
        val orderDate = FormatUtil().getFormattedDate(localDateTime)
        val orderItem = OrderInfo(orderId, cartItemList, orderDate)

        globalManager.orderManager.addOrderItem(orderItem) // 나중에 cartManager에서 영수증 출력하기로 바꾸기

        println(orderItem)

        print("결제가 완료되었습니다. 영수증을 출력하시겠습니까? (y/n): ")
        val answer = readln().lowercase()
        if (answer == "y") {
            globalManager.orderManager.showReceipt()
            globalManager.cartManager.clearMyCart()
        } else {
            println("주문번호: $orderId")
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

    /* 데이터 초기화 */
    private fun initCategoryData(): ArrayList<CategoryInfo> {
        return arrayListOf(
            CategoryInfo(1, "음료"),
            CategoryInfo(2, "푸드"),
            CategoryInfo(3, "상품"),
            CategoryInfo(8, "장바구니"),
            CategoryInfo(9, "결제목록")
        )
    }

    private fun initMenuData(): ArrayList<MenuItem> {
        return arrayListOf(
            Drink(1, 1, "아메리카노", 4500),
            Drink(2, 1, "카페라떼", 5500),
            Drink(3, 1, "카푸치노", 5000),
            Food(1, 2, "카스테라", 4500),
            Food(2, 2, "케이크", 5700),
            Food(3, 2, "마카롱", 2700),
            Product(1, 3, "머그컵", 14000),
            Product(2, 3, "텀블러", 25000),
            Product(3, 3, "원두", 18000)
        )
    }

    private fun readInt(): Int {
        return readln().toIntOrNull() ?: 0
    }
}
