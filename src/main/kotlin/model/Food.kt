package model

class Food(
    itemId: Int,
    categoryId: Int,
    name: String,
    price: Int,
    private val description: String
) : MenuItem(itemId, categoryId, name, price) {

    override fun displayDetailInfo() {
        println("\n${itemId}. $name - ${price}원  |  $description\n")
    }
}
