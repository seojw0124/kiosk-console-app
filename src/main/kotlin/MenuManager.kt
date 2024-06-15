import model.CategoryInfo

class MenuManager(list: ArrayList<MenuItem>) {

    private val drinks = list.filter { it.categoryId == 1 }
    private val foods = list.filter { it.categoryId == 2 }
    private val products = list.filter { it.categoryId == 3 }

    fun getMenuItem(categoryId: Int, itemId: Int): MenuItem? {
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

    fun showDetailMenu(categoryId: Int) {
        val categoryName = when (categoryId) {
            1 -> "음료"
            2 -> "푸드"
            else -> "상품"
        }
        println("""
            
            ====================================
                       << $categoryName 메뉴 >>
        """.trimIndent())
        when (categoryId) {
            1 -> drinks.forEach { it.displaySimpleInfo() }
            2 -> foods.forEach { it.displaySimpleInfo() }
            3 -> products.forEach { it.displaySimpleInfo() }
        }
        println("""
            
            0. 뒤로가기
            ====================================
        """.trimIndent())
    }

    fun getMenuNumber(categoryId: Int): Int? {
        var isExitMenu = false

        var selectedMenuNumber: Int? = null

        while (!isExitMenu) {
            showDetailMenu(categoryId)

            print("메뉴를 선택하세요(0: 뒤로가기): ")
            var isSelectedMenu = false

            while (!isSelectedMenu) {
                try {
                    val itemId = readInt()
                    when (itemId) {
                        in 1..getMenuItemCount(categoryId) -> {
                            isSelectedMenu = true
                            selectedMenuNumber = itemId
                        }
                        0 -> {
                            isExitMenu = true
                            selectedMenuNumber = 0
                            break
                        }
                        else -> throw IndexOutOfBoundsException()
                    }
                } catch (e: Exception) {
                    print("없는 메뉴입니다. 다시 입력해주세요. 메뉴: ")
                    selectedMenuNumber = null
                }
            }
            return selectedMenuNumber
        }
        return selectedMenuNumber
    }
}