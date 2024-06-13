import model.CategoryItem

class Menu(list: ArrayList<AbstractMenu>) {

    private val drinks = list.filter { it.categoryId == 1 }
    private val foods = list.filter { it.categoryId == 2 }
    private val products = list.filter { it.categoryId == 3 }

    fun getMenuItem(categoryId: Int, itemId: Int): AbstractMenu? {
        return when (categoryId) {
            1 -> drinks.find { it.itemId == itemId }
            2 -> foods.find { it.itemId == itemId }
            else -> products.find { it.itemId == itemId }
        }
    }

    fun getMenuItemCount(categoryId: Int): Int {
        return when (categoryId) {
            1 -> drinks.size
            2 -> foods.size
            else -> products.size
        }
    }

    fun showDetailMenu(category: CategoryItem) {
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