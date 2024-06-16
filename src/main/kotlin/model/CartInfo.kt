package model

data class CartInfo(
    val cartId: Int,
    val itemId: Int,
    val itemName: String,
    val price: Int,
    var quantity: Int
)
