import model.BeverageItem
import model.CategoryItem
import model.FoodItem
import model.ProductItem

class Menu {

    private val beverageItemMenus: ArrayList<BeverageItem> = arrayListOf(
        BeverageItem(1, 1, "아메리카노", 4500),
        BeverageItem(2, 1, "카페라떼", 5500),
        BeverageItem(3, 1, "카푸치노", 5000)
    )

    private val foodItemMenus: ArrayList<FoodItem> = arrayListOf(
        FoodItem(1, 2, "카스테라", 4500),
        FoodItem(2, 2, "케이크", 5700),
        FoodItem(3, 2, "마카롱", 2700)
    )

    private val productItemMenus: ArrayList<ProductItem> = arrayListOf(
        ProductItem(1, 3, "머그컵", 14000),
        ProductItem(2, 3, "텀블러", 25000),
        ProductItem(3, 3, "원두", 18000)
    )

    fun showDetailMenuByCategory(category: CategoryItem) {
        println("""
            ====================================
                       << ${category.categoryName} 메뉴 >>
        """.trimIndent())
        when (category.categoryId) {
            1 -> beverageItemMenus.forEach { it.displayInfo() }
            2 -> foodItemMenus.forEach { it.displayInfo() }
            3 -> productItemMenus.forEach { it.displayInfo() }
        }
        println("""
            
            0. 뒤로가기
            ====================================
        """.trimIndent())
    }
}