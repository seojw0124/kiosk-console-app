import model.CategoryInfo

class CategoryManager(private val categories: ArrayList<CategoryInfo>) {

    fun getItemCount(): Int {
        return categories.size - 1 // 장바구니 카테고리는 제외
    }

    fun showCategory() {
        println("""
            ====================================
                       << 정우 카페 >>
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