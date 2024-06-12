class Product(
    private val id: Int,
    private val categoryId: Int,
    private val name: String,
    private val price: Int
) : AbstractMenu() {
    override fun displayInfo() {
        println("${id}. $name - ${price}원")
    }
}