import model.*
import utils.FormatUtil
import java.time.LocalDateTime
import kotlin.system.exitProcess

class Kiosk {
    private lateinit var categoryManager: CategoryManager
    private lateinit var menuManager: MenuManager
    private lateinit var cartManager: CartManager
    private lateinit var orderManager: OrderManager

    private var money: Int = 0

    fun start() {
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
            categoryManager.showList()
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
                    in 1..categoryManager.getItemCount() -> categoryId
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
            menuManager.showDetailItem(categoryId)
            print("메뉴를 선택하세요(0: 뒤로가기): ")
            val itemId = getSelectedMenuItemId(categoryId)
            if (itemId == 0) break

            val item = menuManager.getItem(categoryId, itemId)
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
                    in 1..menuManager.getItemCount(categoryId) -> itemId
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
                            if (cartManager.isEmpty()) {
                                1
                            } else {
                                cartManager.getLastItemId() + 1
                            }
                        val cartInfo = CartInfo(
                            cartId,
                            item.itemId,
                            item.name,
                            item.price,
                            quantity
                        )
                        cartManager.addItem(cartInfo, item.categoryId)
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
        if (cartManager.isEmpty()) {
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
        if (money < cartManager.getItemTotalPrice()) {
            println("\n현재 잔액은 ${FormatUtil().decimalFormat(money)}원으로 ${FormatUtil().decimalFormat(cartManager.getItemTotalPrice() - money)}원이 부족해서 결제할 수 없습니다.\n")
            return
        }

        val cartItemList = cartManager.getMyItemList()
        val orderId =
            if (orderManager.isEmpty()) {
                1
            } else {
                orderManager.getLastItemId() + 1
            }

        // 결제 시간은 현재 시간으로 설정(2024-06-14 00:00:00)
        val localDateTime = LocalDateTime.now().toString()
        val orderDate = FormatUtil().getFormattedDate(localDateTime)
        val orderItem = OrderInfo(orderId, cartItemList, orderDate)

        orderManager.addItem(orderItem)

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
            Drink(1, 1, "아메리카노", 4500, "진한 에스프레소에 시원한 정수물과 얼음을 더해 가장 부드럽고 시원하게 즐길 수 있는 커피"),
            Drink(2, 1, "카페라떼", 5500, "풍부하고 진한 농도의 에스프레소가 시원한 우유와 얼음을 만나 고소함과 시원함을 즐길 수 있는 커피 라떼"),
            Drink(3, 1, "카푸치노", 5000, "풍부하고 진한 에스프레소에 신선한 우유와 우유 거품이 얼음과 함께 들어간 시원하고 부드러운 커피 음료"),
            Drink(4, 1, "바닐라 라떼", 6000, "더욱 깊은 커피의 맛과 향에 깔끔한 무지방 우유와 부드러운 바닐라 시럽이 들어간 달콤하고 진한 커피 라떼"),
            Food(1, 2, "카스테라", 4500, "부드러운 생크림이 듬뿍 들어있는 촉촉한 카스테라"),
            Food(2, 2, "치즈 케이크", 5700, "진한 치즈의 맛을 맛볼 수 있는 케이크"),
            Food(3, 2, "마카롱", 2700, "겉은 바삭하고 속은 쫄깃한 달콤한 마카롱"),
            Product(1, 3, "머그컵", 14000),
            Product(2, 3, "텀블러", 25000),
        )
    }
}
