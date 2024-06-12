import model.CategoryItem

class Category {

    private val categories: ArrayList<CategoryItem> = arrayListOf(
        CategoryItem(1, "음료"),
        CategoryItem(2, "푸드"),
        CategoryItem(3, "상품")
    )

    fun getCategoryItem(categoryId: Int): CategoryItem? {
        return categories.find { it.categoryId == categoryId }
    }

    fun showCategory() {
        println("""
            ====================================
                       << 카페 메뉴 >>
        """.trimIndent())
        categories.forEach {
            println("${it.categoryId}. ${it.categoryName}")
        }
        println("""
            
            0. 프로그램 종료
            ====================================
        """.trimIndent())
        print("메뉴를 선택하세요: ")
    }
}