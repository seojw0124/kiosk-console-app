open class MenuItem(val itemId: Int, val categoryId: Int, val name: String, val price: Int) {

    fun displaySimpleInfo() {
        println("${itemId}. $name")
    }

    fun displayDetailInfo() {
        println()
        println("${itemId}. $name - ${price}원")
        println()
    }
}