import model.CategoryItem

class Menu {

    private val beverageMenus: ArrayList<Beverage> = arrayListOf(
        Beverage(1, 1, "아메리카노", 4500),
        Beverage(2, 1, "카페라떼", 5500),
        Beverage(3, 1, "카푸치노", 5000)
    )

    private val foodMenus: ArrayList<Food> = arrayListOf(
        Food(1, 2, "카스테라", 4500),
        Food(2, 2, "케이크", 5700),
        Food(3, 2, "마카롱", 2700)
    )

    private val productMenus: ArrayList<Product> = arrayListOf(
        Product(1, 3, "머그컵", 14000),
        Product(2, 3, "텀블러", 25000),
        Product(3, 3, "원두", 18000)
    )

    fun showDetailMenuByCategory(category: CategoryItem) {
        println("""
            ====================================
                       << ${category.categoryName} 메뉴 >>
        """.trimIndent())
        when (category.categoryId) {
            1 -> beverageMenus.forEach { it.displayInfo() }
            2 -> foodMenus.forEach { it.displayInfo() }
            3 -> productMenus.forEach { it.displayInfo() }
        }
        println("""
            
            0. 뒤로가기
            ====================================
        """.trimIndent())
    }
}