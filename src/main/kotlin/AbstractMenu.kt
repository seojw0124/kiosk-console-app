abstract class AbstractMenu(val id: Int, val categoryId: Int, val name: String, val price: Int) {
    abstract fun displayInfo()
}