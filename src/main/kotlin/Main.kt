import model.CategoryItem

fun main() {

    val categories = Category(initCategoryData())
    val menu = Menu(initMenuData())

    println("***** 정우 카페에 오신 것을 환영합니다 *****")

    while (true) {
        categories.showCategory()

        when (val categoryId = readInt()) {
            in 1..3 -> {
                val category = categories.getCategoryItem(categoryId)
                category?.let { menu.showDetailMenu(it) }
                while (true) {
                    print("메뉴를 선택하세요: ")
                    val itemId = readInt()
                    if (itemId == 0) break

                    val item = menu.getItem(categoryId, itemId)
                    item?.let {
                        item.displayInfo()
                        print("수량을 입력하세요: ")
                        val quantity = readInt()
                        if (quantity > 0) {
                            print("장바구니에 담으시겠습니까? (y/n): ")
                            val answer = readln()
                            if (answer == "y") {
                                menu.addToCart(item, quantity)
                            }
                        } else {
                            println("수량은 1 이상이어야 합니다.")
                        }
                    }
                }
            }
            0 -> break
            else -> println("잘못된 번호입니다.")
        }
    }
}

fun initCategoryData(): ArrayList<CategoryItem> {
    val categories: ArrayList<CategoryItem> = arrayListOf(
        CategoryItem(1, "음료"),
        CategoryItem(2, "푸드"),
        CategoryItem(3, "상품")
    )

    return categories
}

fun initMenuData(): List<AbstractMenu> {
    return listOf(
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
