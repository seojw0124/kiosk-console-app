import model.CategoryItem

fun main() {

    val categories = Category(initCategoryData())
    val triple = initMenuData() // Triple 클래스를 사용하여 세 가지 종류의 메뉴를 한 번에 초기화
    val menu = Menu(triple.first, triple.second, triple.third)

    println("***** 정우 카페에 오신 것을 환영합니다 *****")

    while (true) {
        categories.showCategory()
        val categoryId = readln().toIntOrNull() ?: 0

        when (categoryId) {
            in 1..3 -> {
                val categoryItem = categories.getCategoryItem(categoryId)
                categoryItem?.let { menu.showDetailMenuByCategory(it) }
                while (true) {
                    print("메뉴를 선택하세요: ")
                    val itemId = readln().toIntOrNull() ?: 0
                    if (itemId == 0) break
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

fun initMenuData(): Triple<ArrayList<Drink>, ArrayList<Food>, ArrayList<Product>> {
    val drinks: ArrayList<Drink> = arrayListOf(
        Drink(1, 1, "아메리카노", 4500),
        Drink(2, 1, "카페라떼", 5500),
        Drink(3, 1, "카푸치노", 5000)
    )

    val foods: ArrayList<Food> = arrayListOf(
        Food(1, 2, "카스테라", 4500),
        Food(2, 2, "케이크", 5700),
        Food(3, 2, "마카롱", 2700)
    )

    val products: ArrayList<Product> = arrayListOf(
        Product(1, 3, "머그컵", 14000),
        Product(2, 3, "텀블러", 25000),
        Product(3, 3, "원두", 18000)
    )

    return Triple(drinks, foods, products)
}
