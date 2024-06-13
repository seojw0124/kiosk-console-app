import model.CategoryItem

fun main() {

    val categories = Category(initCategoryData())
    val menu = Menu(initMenuData())
    val cart = Cart()

    println("***** 정우 카페에 오신 것을 환영합니다 *****")

    runKiosk(categories, menu, cart)
}

private fun runKiosk(categories: Category, menu: Menu, cart: Cart) {
    while (true) {
        categories.showCategory()
        val categoryId = readInt()

        when (categoryId) {
            in 1..categories.getItemCount() -> {
                showDetailMenuByCategory(categories, categoryId, menu, cart)
            }
            9 -> {
                cart.showCartList()
                print("메뉴를 선택하세요: ")
                val selectedCartMenu = readInt()
                when (selectedCartMenu) {
                    1 -> {
                        println("결제가 완료되었습니다.")
                        break
                    }
                    0 -> continue
                    else -> println("잘못된 번호입니다.")
                }
            }
            0 -> {
                break
            }
            else -> {
                println("없는 카테고리입니다. 다시 입력해주세요.")
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
    while (true) {
        print("메뉴를 선택하세요(0: 뒤로가기): ")
        val itemId = readInt()

        when (itemId) {
            0 -> {
                break
            }
            in 1..menu.getMenuItemCount(categoryId) -> {
                val item = menu.getMenuItem(categoryId, itemId)
                item?.let {
                    item.displayInfo()
                    val quantity = getQuantity()
                    checkAddMenuItemToCart(cart, item, quantity)
                }
            }
            else -> {
                println("없는 메뉴입니다.")
                continue
            }
        }
    }
}

private fun checkAddMenuItemToCart(cart: Cart, item: AbstractMenu, quantity: Int) {
    while (true) {
        print("장바구니에 담으시겠습니까? (y/n): ")
        val answer = readln().lowercase()

        when (answer) {
            "y" -> {
                cart.addCartItem(item, quantity)
                break
            }
            "n" -> {
                println("장바구니에 담기를 취소했습니다.")
                break
            }
            else -> println("잘못된 입력입니다.")
        }
    }
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
    val categories: ArrayList<CategoryItem> = arrayListOf(
        CategoryItem(1, "음료"),
        CategoryItem(2, "푸드"),
        CategoryItem(3, "상품"),
        CategoryItem(9, "장바구니")
    )

    return categories
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
