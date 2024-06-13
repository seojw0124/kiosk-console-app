package model

data class CartItem(
//    val cartId: Int,  cartId는 itemId로 대체
    val itemId: Int,
    val itemName: String,
    val price: Int,
    val quantity: Int
)
