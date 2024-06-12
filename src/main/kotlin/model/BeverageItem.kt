package model

import AbstractMenu

class BeverageItem(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val price: Int
) : AbstractMenu() {
    override fun displayInfo() {
        println("${id}. ${name} - ${price}원")
    }
}