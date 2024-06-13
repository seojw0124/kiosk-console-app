class Drink(
    itemId: Int,
    categoryId: Int,
    name: String,
    price: Int
) : AbstractMenu(itemId, categoryId, name, price) {
    override fun displayInfo() {
        println("${itemId}. $name - ${price}원")
    }
}