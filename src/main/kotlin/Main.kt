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
                    print("메뉴를 선택하세요: ")
                    val itemId = readln().toIntOrNull() ?: 0
                    if (itemId == 0) break

//                    val itemId = readln().toIntOrNull() ?: 0
//                    if (itemId == 0) break
//                    val item = menu.getItem(categoryId, itemId)
//                    item?.let {
//                        println("${it.name}을 선택하셨습니다.")
//                        println("수량을 입력하세요: ")
//                        val quantity = readln().toIntOrNull() ?: 0
//                        if (quantity == 0) {
//                            println("수량을 다시 입력해주세요.")
//                            continue
//                        }
//                        val order = Order(it, quantity)
//                        println("주문이 완료되었습니다. 주문 내역을 확인하세요.")
//                        println(order)
//                    } ?: println("잘못된 메뉴 번호입니다.")
                }
            }
            0 -> break
            else -> println("잘못된 번호입니다.")
        }
    }
}
