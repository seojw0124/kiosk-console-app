import model.CartInfo
import model.CategoryInfo
import model.OrderInfo
import model.UserInfo
import utils.FormatUtil
import java.time.LocalDateTime

val money = 100000

fun main() {
    val globalManager = init()
    println("***** 정우 카페에 오신 것을 환영합니다 *****")

    while (true) {
        val money = inputMoney()
        runKiosk(globalManager)
    }
}

fun init(): GlobalManager {
    val categoryManager = CategoryManager(initCategoryData())
    val menuManager = MenuManager(initMenuData())
    val cartManager = CartManager()
    val orderManager = OrderManager()

    return GlobalManager(categoryManager, menuManager, cartManager, orderManager)
}

fun inputMoney(): Int {
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

private fun runKiosk(
    globalManager: GlobalManager
) {
    var isExitKiosk = false

    while (!isExitKiosk) {
        globalManager.categoryManager.showCategory()
        var isSelectedCategory = false

        while (!isSelectedCategory) {
            try {
                val categoryId = readInt()
                when (categoryId) {
                    in 1..globalManager.categoryManager.getItemCount() -> {
                        var isExitMenu = false

                        while (!isExitMenu) {
                            globalManager.menuManager.showDetailMenu(categoryId)

                            print("메뉴를 선택하세요(0: 뒤로가기): ")
                            var isSelectedMenu = false

                            while (!isSelectedMenu) {
                                try {
                                    val itemId = readInt()
                                    when (itemId) {
                                        in 1..globalManager.menuManager.getMenuItemCount(categoryId) -> {
                                            val item = globalManager.menuManager.getMenuItem(categoryId, itemId)
                                            item?.let {
                                                isSelectedMenu = true
                                                item.displayDetailInfo()
                                                val quantity = getQuantity()
                                                var isCancelAddToCart = false

                                                while (!isCancelAddToCart) {
                                                    print("장바구니에 담으시겠습니까? (y/n): ")

                                                    while (true) {
                                                        try {
                                                            val answer = readln().lowercase()
                                                            when (answer) {
                                                                "y" -> {
                                                                    if (money < item.price * quantity) {
                                                                        println("소지금이 부족합니다. 다른 상품을 선택해주세요. 현재 소지금: ${FormatUtil().decimalFormat(money)}원")
                                                                        return
                                                                    }
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
                                                                    isCancelAddToCart = true
                                                                    break
                                                                }
                                                                "n" -> {
                                                                    println("장바구니에 담기를 취소했습니다.")
                                                                    isCancelAddToCart = true
                                                                    break
                                                                }
                                                                else -> throw Exception()
                                                            }
                                                        } catch (e: Exception) {
                                                            print("잘못된 입력입니다. 다시 입력해주세요: ")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        0 -> {
                                            isExitMenu = true
                                            break
                                        }
                                        else -> throw IndexOutOfBoundsException()
                                    }
                                } catch (e: Exception) {
                                    print("없는 메뉴입니다. 다시 입력해주세요. 메뉴: ")
                                }
                            }
                        }
                        isSelectedCategory = true
                    }
                    8 -> {
                        globalManager.cartManager.showMyCart(money)
                        print("메뉴를 선택하세요: ")
                        val selectedCartMenu = readInt()
                        when (selectedCartMenu) {
                            1 -> {
                                isSelectedCategory = true
                                val cartItemList = globalManager.cartManager.getMyCartItemList()
                                println("cartItemList: $cartItemList")
                                val orderId =
                                    if (globalManager.orderManager.isOrderListEmpty()) {
                                        1
                                    } else {
                                        globalManager.orderManager.getLastOrderItemId() + 1
                                    }

                                // 주문 시간은 현재 시간으로 설정(2024-06-14 00:00:00)
                                val localDateTime = LocalDateTime.now().toString()
                                val orderDate = FormatUtil().getFormattedDate(localDateTime)

                                val orderItem = OrderInfo(
                                    orderId,
                                    cartItemList,
                                    orderDate
                                )
                                globalManager.orderManager.addOrderItem(orderItem)
                                globalManager.cartManager.clearMyCart()
                                println("결제가 완료되었습니다.")
                            }
                            0 -> {
                                isSelectedCategory = true
                                break
                            }
                            else -> println("없는 번호입니다. 다시 입력해주세요.")
                        }
                    }
                    0 -> {
                        isExitKiosk = true // 외부 루프를 종료하도록 설정
                        isSelectedCategory = true // 내부 루프를 종료하도록 설정
                        break
                    }
                    else -> throw IndexOutOfBoundsException()
                }
            } catch (e: Exception) {
                print("없는 카테고리입니다. 다시 입력해주세요. 카테고리: ")
            }
        }
    }
}

private fun showDetailMenuByCategory(globalManager: GlobalManager, categoryId: Int) {
    val category = globalManager.categoryManager.getCategoryItem(categoryId)
    category?.let {
        var isExitMenu = false

        while (!isExitMenu) {
            globalManager.menuManager.showDetailMenu(it.categoryId)

            print("메뉴를 선택하세요(0: 뒤로가기): ")
            var isSelectedMenu = false

            while (!isSelectedMenu) {
                try {
                    val itemId = readInt()
                    when (itemId) {
                        in 1..globalManager.menuManager.getMenuItemCount(categoryId) -> {
                            val item = globalManager.menuManager.getMenuItem(categoryId, itemId)
                            item?.let {
                                isSelectedMenu = true
                                item.displayDetailInfo()
                                val quantity = getQuantity()
                                checkAddMenuItemToCart(globalManager.cartManager, item, quantity)
                            }
                        }
                        0 -> {
                            isExitMenu = true
                            break
                        }
                        else -> throw IndexOutOfBoundsException()
                    }
                } catch (e: Exception) {
                    print("없는 메뉴입니다. 다시 입력해주세요. 메뉴: ")
                }
            }
        }
    }
}

private fun checkAddMenuItemToCart(cartManager: CartManager, item: MenuItem, quantity: Int) {
    var isCancelAddToCart = false

    while (!isCancelAddToCart) {
        print("장바구니에 담으시겠습니까? (y/n): ")

        while (true) {
            try {
                val answer = readln().lowercase()
                when (answer) {
                    "y" -> {
                        addItemToCart(item, quantity, cartManager)
                        isCancelAddToCart = true
                        break
                    }
                    "n" -> {
                        println("장바구니에 담기를 취소했습니다.")
                        isCancelAddToCart = true
                        break
                    }
                    else -> throw Exception()
                }
            } catch (e: Exception) {
                print("잘못된 입력입니다. 다시 입력해주세요: ")
            }
        }
    }
}

private fun addItemToCart(item: MenuItem, quantity: Int, cartManager: CartManager) {
    if (money < item.price * quantity) {
        println("소지금이 부족합니다. 다른 상품을 선택해주세요. 현재 소지금: ${FormatUtil().decimalFormat(money)}원")
        return
    }
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

    return
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

/* 결제하기 */
fun addCartItemToOrder(globalManager: GlobalManager) {
    val cartItemList = globalManager.cartManager.getMyCartItemList()
    println("cartItemList: $cartItemList")
    val orderId =
        if (globalManager.orderManager.isOrderListEmpty()) {
            1
        } else {
            globalManager.orderManager.getLastOrderItemId() + 1
        }

    // 주문 시간은 현재 시간으로 설정(2024-06-14 00:00:00)
    val localDateTime = LocalDateTime.now().toString()
    val orderDate = FormatUtil().getFormattedDate(localDateTime)

    val orderItem = OrderInfo(
        orderId,
        cartItemList,
        orderDate
    )
    globalManager.orderManager.addOrderItem(orderItem)
    globalManager.cartManager.clearMyCart()
    println("결제가 완료되었습니다.")
}

/* 데이터 초기화 */
fun initCategoryData(): ArrayList<CategoryInfo> {
    return arrayListOf(
        CategoryInfo(1, "음료"),
        CategoryInfo(2, "푸드"),
        CategoryInfo(3, "상품"),
        CategoryInfo(8, "장바구니"),
        CategoryInfo(9, "결제목록")
    )
}

fun initMenuData(): ArrayList<MenuItem> {
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

fun readInt(): Int {
    return readln().toIntOrNull() ?: 0
}
