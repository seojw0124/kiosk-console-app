import model.CategoryInfo

class CategoryManager(private val categoryList: ArrayList<CategoryInfo>) {

    fun getCategoryItemCount(): Int {
        return categoryList.size - 1 // 장바구니 카테고리는 제외
    }

    fun showCategoryList() {
        println("""
            ======================================
                       << 정우 카페 >>
        """.trimIndent())
        categoryList.forEach {
            if (it.categoryId == 9) println()
            println("${it.categoryId}. ${it.categoryName}")
        }
        println("""
            
            0. 프로그램 종료
            ======================================
        """.trimIndent())
        print("카테고리를 선택하세요: ")
    }
}