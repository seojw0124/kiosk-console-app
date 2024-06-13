abstract class AbstractMenu(val itemId: Int, val categoryId: Int, val name: String, val price: Int) {
    abstract fun displayInfo()
}