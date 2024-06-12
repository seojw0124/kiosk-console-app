fun main() {

    val menu = Menu()
    val category = Category()

    println("***** 정우 카페에 오신 것을 환영합니다 *****")

    while (true) {
        category.showCategory()
        val categoryId = readln().toIntOrNull() ?: 0

        when (categoryId) {
            in 1..3 -> {
                val categoryItem = category.getCategoryItem(categoryId)
                categoryItem?.let { menu.showDetailMenuByCategory(it) }
                while (true) {
                    print("메뉴를 선택하세요(뒤로가기: 0): ")
                    val itemId = readln().toIntOrNull() ?: 0
                    if (itemId == 0) break
                }
            }
            0 -> break
            else -> println("잘못된 번호입니다.")
        }
    }
}
