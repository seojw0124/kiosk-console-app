class Drink(
    id: Int,
    categoryId: Int,
    name: String,
    price: Int
) : AbstractMenu(id, categoryId, name, price) {
    override fun displayInfo() {
        println("${id}. $name - ${price}원")
    }
}