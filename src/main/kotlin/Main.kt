import model.CartItem
import model.CategoryItem
import model.UserItem

private lateinit var currentUser: UserItem // 질문) 이렇게 전역변수로 선언해도 되나요?

fun main() {

    val user = User()
    val categories = Category(initCategoryData())
    val menu = Menu(initMenuData())
    val cart = Cart()
    val order = Order()

    println("***** 정우 카페에 오신 것을 환영합니다 *****")

    while (true) {
        login(user)
        runKiosk(categories, menu, cart, order)
    }
}

private fun login(user: User) {
    val userName = inputMyInfo("name")
    val money = inputMyInfo("money")

    val userId =
        if (user.isUserListEmpty()) { // 질문) user.userList.isEmpty()로 하면 안되나요? (userList가 public으로 바꿔도 되나요?)
            1
        } else {
            user.getLastCurrentUserId() + 1
        }
    user.saveUserInfo(UserItem(userId, userName as String, money as Int))
    currentUser = user.setCurrentUser(userId)
    println("${currentUser.userName} 님 <정우 카페>에 오신 것을 환영합니다!!!")
}

private fun inputMyInfo(type: String): Any? {
    return when (type) {
        "name" -> {
            println("이름을 입력해주세요.")
            while (true) {
                try {
                    val originName = readln()
                    if (originName.first() != '_' && originName.first() != '!') { // orginName.first() 가 NoSuchElementException 발생 -> 이름 입력 안하면 catch로 넘어감
                        return originName
                    } else {
                        println("이름을 다시 입력해주세요.")
                    }
                } catch (e:Exception) {
                    println("이름을 다시 입력해주세요.")
                }
            }
        }
        "money" -> {
            println("소지하고 계신 금액을 입력해주세요.")
            while (true) {
                try {
                    val originMoney = readInt()
                    if (originMoney > 0) {
                        return originMoney
                    } else {
                        println("소지하고 계신 금액을 다시 입력해주세요")
                    }
                } catch (e:Exception) {
                    println("소지하고 계신 금액을 다시 입력해주세요")
                }
            }
        }
        else -> null
    }
}

private fun runKiosk(
    categories: Category,
    menu: Menu,
    cart: Cart,
    order: Order
) {
    var isExitKiosk = false

    while (!isExitKiosk) {
        categories.showCategory()
        var isSelectedCategory = false

        while (!isSelectedCategory) {
            try {
                val categoryId = readInt()
                when (categoryId) {
                    in 1..categories.getItemCount() -> {
                        showDetailMenuByCategory(categories, categoryId, menu, cart)
                        isSelectedCategory = true
                    }
                    8 -> {
                        cart.showMyCartList()
                        print("메뉴를 선택하세요: ")
                        val selectedCartMenu = readInt()
                        when (selectedCartMenu) {
                            1 -> {
                                println("결제가 완료되었습니다.")
                                isSelectedCategory = true
                                break
                            }
                            0 -> {
                                isSelectedCategory = true
                                break
                            }
                            else -> {
                                println("없는 번호입니다. 다시 입력해주세요.")
                            }
                        }
                    }
                    9 -> {
                        order.showMyOrderList(currentUser.userId)
                    }
                    0 -> {
                        isExitKiosk = true // 외부 루프를 종료하도록 설정
                        isSelectedCategory = true // 내부 루프를 종료하도록 설정
                        break
                    }
                    else -> {
                        throw IndexOutOfBoundsException()
                    }
                }
            } catch (e:Exception) {
                print("없는 카테고리입니다. 다시 입력해주세요. 카테고리: ")
            }
        }
    }
}

private fun showDetailMenuByCategory(categories: Category, categoryId: Int, menu: Menu, cart: Cart) {
    val category = categories.getCategoryItem(categoryId)
    category?.let {
        menu.showDetailMenu(it)
        selectMenuItem(menu, categoryId, cart)
    }
}

private fun selectMenuItem(menu: Menu, categoryId: Int, cart: Cart) {
    var isExitMenu = false

    while (!isExitMenu) {
        print("메뉴를 선택하세요(0: 뒤로가기): ")
        var isSelectedMenu = false

        while (!isSelectedMenu) {
            try {
                val itemId = readInt()
                when (itemId) {
                    in 1..menu.getMenuItemCount(categoryId) -> {
                        val item = menu.getMenuItem(categoryId, itemId)
                        item?.let {
                            isSelectedMenu = true
                            item.displayInfo()
                            val quantity = getQuantity()
                            checkAddMenuItemToCart(cart, item, quantity)
                        }
                    }
                    0 -> {
                        isExitMenu = true
                        break
                    }
                    else -> {
                        throw IndexOutOfBoundsException()
                    }
                }
            } catch (e:Exception) {
                print("없는 메뉴입니다. 다시 입력해주세요. 메뉴: ")
            }
        }
    }
}

private fun checkAddMenuItemToCart(cart: Cart, item: AbstractMenu, quantity: Int) {
    var isCancelAddToCart = false

    while (!isCancelAddToCart) {
        print("장바구니에 담으시겠습니까? (y/n): ")

        while (true) {
            try {
                val answer = readln().lowercase()
                when (answer) {
                    "y" -> {
                        addItemToCart(item, quantity, cart)
                        isCancelAddToCart = true
                        break
                    }
                    "n" -> {
                        println("장바구니에 담기를 취소했습니다.")
                        isCancelAddToCart = true
                        break
                    }
                    else -> {
                        // y, n 이외의 값이 입력되었을 때 예외처리
                        throw Exception()
                    }
                }
            } catch (e:Exception) {
                print("잘못된 입력입니다. 다시 입력해주세요: ")
            }
        }
    }
}

private fun addItemToCart(item: AbstractMenu, quantity: Int, cart: Cart) {
    if (currentUser.money < item.price * quantity) {
        println("소지금이 부족합니다. 다른 상품을 선택해주세요.")
        return
    }
    val cartId =
        if (cart.isCartListEmpty()) {
        1
    } else {
        cart.getLastCartItemId() + 1
    }
    val cartItem = CartItem(
        cartId,
        currentUser.userId,
        item.itemId,
        item.name,
        item.price,
        quantity
    )
    cart.addCartItem(cartItem, item.categoryId)
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

fun initCategoryData(): ArrayList<CategoryItem> {
    return arrayListOf(
        CategoryItem(1, "음료"),
        CategoryItem(2, "푸드"),
        CategoryItem(3, "상품"),
        CategoryItem(8, "장바구니"),
        CategoryItem(9, "결제목록")
    )
}

fun initMenuData(): ArrayList<AbstractMenu> {
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
