package model

open class MenuItem(
    val itemId: Int,
    val categoryId: Int,
    val name: String,
    val price: Int,
) {

    fun displaySimpleInfo() {
        println("${itemId}. $name - ${price}원")
    }

    open fun displayDetailInfo() {
        println("\n${itemId}. $name - ${price}원\n")
    }
}