import model.Beverage

fun main() {

    val menu = Menu()

    println("***** 정우 카페에 오신 것을 환영합니다 *****")

    while (true) {
        showHomeMenu()
        val categoryId = readln().toIntOrNull() ?: 0

        when (categoryId) {
            in 1..3 -> menu.showDetailMenu(categoryId)
            0 -> break
            else -> println("잘못된 번호입니다.")
        }
    }
}

fun showHomeMenu() {
    println("""
        ====================================
                   << 카페 메뉴 >>
        1. 음료
        2. 푸드
        3. 상품
        
        0. 프로그램 종료
        ====================================
    """.trimIndent())
    print("메뉴를 선택하세요: ")
}