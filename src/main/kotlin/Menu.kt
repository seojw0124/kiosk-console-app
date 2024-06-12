import model.Beverage
import model.Food
import model.Product

class Menu {

    private val beverageMenu: ArrayList<Beverage> = arrayListOf(
        Beverage(1, "아메리카노", 4500),
        Beverage(2, "카페라떼", 5500),
        Beverage(3, "카푸치노", 5000)
    )

    private val foodMenu: ArrayList<Food> = arrayListOf(
        Food(1, "카스테라", 4500),
        Food(2, "케이크", 5700),
        Food(3, "마카롱", 2700)
    )

    private val productMenu: ArrayList<Product> = arrayListOf(
        Product(1, "머그컵", 14000),
        Product(2, "텀블러", 25000),
        Product(3, "원두", 18000)
    )

    fun showDetailMenu(categoryId: Int) {
        println("""
            [${categoryToString(categoryId)} 메뉴]
            ${getMenu(categoryId)}
        """.trimIndent())
    }

    private fun categoryToString(categoryId: Int): String {
        return when (categoryId) {
            1 -> "음료"
            2 -> "푸드"
            3 -> "상품"
            else -> "잘못된 번호"
        }
    }

    private fun getMenu(categoryId: Int): String {
        return when (categoryId) {
            1 -> beverageMenu.joinToString("\n")
            2 -> foodMenu.joinToString("\n")
            3 -> productMenu.joinToString("\n")
            else -> "잘못된 번호"
        }
    }
}