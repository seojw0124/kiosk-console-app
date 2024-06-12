import model.CategoryItem

class Menu(
    private val drinks: ArrayList<Drink>,
    private val foods: ArrayList<Food>,
    private val products: ArrayList<Product>
) {

    fun showDetailMenuByCategory(category: CategoryItem) {
        println("""
            ====================================
                       << ${category.categoryName} 메뉴 >>
        """.trimIndent())
        when (category.categoryId) {
            1 -> drinks.forEach { it.displayInfo() }
            2 -> foods.forEach { it.displayInfo() }
            3 -> products.forEach { it.displayInfo() }
        }
        println("""
            
            0. 뒤로가기
            ====================================
        """.trimIndent())
    }
}