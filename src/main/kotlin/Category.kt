import model.CategoryItem

class Category(private val categories: ArrayList<CategoryItem>) {

    fun getCategoryItem(categoryId: Int): CategoryItem? {
        return categories.find { it.categoryId == categoryId }
    }

    fun getItemCount(): Int {
        return categories.size - 1 // 장바구니 카테고리는 제외
    }

    fun showCategory() {
        println("""
            ====================================
                       << 카페 메뉴 >>
        """.trimIndent())
        categories.forEach {
            if (it.categoryId == 8) println()
            println("${it.categoryId}. ${it.categoryName}")
        }
        println("""
            
            0. 프로그램 종료
            ====================================
        """.trimIndent())
        print("카테고리를 선택하세요: ")
    }
}