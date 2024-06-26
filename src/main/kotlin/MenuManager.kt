import model.MenuItem

class MenuManager(list: ArrayList<MenuItem>) {

    private val drinks = list.filter { it.categoryId == 1 }
    private val foods = list.filter { it.categoryId == 2 }
    private val products = list.filter { it.categoryId == 3 }

    fun getItem(categoryId: Int, itemId: Int): MenuItem? {
        return when (categoryId) {
            1 -> drinks.find { it.itemId == itemId }
            2 -> foods.find { it.itemId == itemId }
            else -> products.find { it.itemId == itemId }
        }
    }

    fun getItemCount(categoryId: Int): Int {
        return when (categoryId) {
            1 -> drinks.size
            2 -> foods.size
            else -> products.size
        }
    }

    fun showDetailItem(categoryId: Int) {
        val categoryName = when (categoryId) {
            1 -> "음료"
            2 -> "푸드"
            else -> "상품"
        }
        println("""
            
            ======================================
                       << $categoryName 메뉴 >>
        """.trimIndent())
        when (categoryId) {
            1 -> drinks.forEach { it.displaySimpleInfo() }
            2 -> foods.forEach { it.displaySimpleInfo() }
            else -> products.forEach { it.displaySimpleInfo() }
        }
        println("""
            
            0. 뒤로가기
            ======================================
        """.trimIndent())
    }
}