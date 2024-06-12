import model.CategoryItem
import utils.KoreanUtil

class Menu(list: ArrayList<AbstractMenu>) {

    private val drinks = list.filter { it.categoryId == 1 }
    private val foods = list.filter { it.categoryId == 2 }
    private val products = list.filter { it.categoryId == 3 }

    fun getItem(categoryId: Int, itemId: Int): AbstractMenu? {
        return when (categoryId) {
            1 -> drinks.find { it.id == itemId }
            2 -> foods.find { it.id == itemId }
            3 -> products.find { it.id == itemId }
            else -> null
        }
    }

    fun getMenuItemCount(categoryId: Int): Int {
        return when (categoryId) {
            1 -> drinks.size
            2 -> foods.size
            3 -> products.size
            else -> 0
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