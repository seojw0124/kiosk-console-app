import model.CategoryItem

fun main() {

    val categories = Category(initCategoryData())
    val menu = Menu(initMenuData())
    val cart = Cart()

    println("***** 정우 카페에 오신 것을 환영합니다 *****")

    while (true) {
        categories.showCategory()

        when (val categoryId = readInt()) {
            in 1..categories.getItemCount() -> {
                val category = categories.getCategoryItem(categoryId)
                category?.let { menu.showDetailMenu(it) }
                while (true) {
                    print("메뉴를 선택하세요: ")
                    val itemId = readInt()

                    when (itemId) {
                        0 -> break
                        in 1..menu.getMenuItemCount(categoryId) -> {
                            val item = menu.getItem(categoryId, itemId)
                            item?.let {
                                item.displayInfo()
                                print("수량을 입력하세요: ")
                                val quantity = readInt()
                                if (quantity > 0) {
                                    print("장바구니에 담으시겠습니까? (y/n): ")
                                    val answer = readln()
                                    when (answer) {
                                        "y" -> cart.addItem(item, quantity)
                                        "n" -> println("장바구니에 담기를 취소했습니다.")
                                        else -> println("잘못된 입력입니다.")
                                    }
                                } else {
                                    println("수량은 1 이상이어야 합니다.")
                                }
                            }
                        }
                        else -> {
                            println("없는 메뉴입니다.")
                            continue
                        }
                    }
                }
            }
            9 -> {
                cart.showCartList()
                print("메뉴를 선택하세요: ")
                when (readInt()) {
                    1 -> {
                        println("결제가 완료되었습니다.")
                        break
                    }
                    0 -> continue
                    else -> println("잘못된 번호입니다.")
                }
            }
            0 -> break
            else -> println("없는 카테고리입니다.")
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
