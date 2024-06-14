package model

data class OrderInfo(
    val orderId: Int,
    val userId: Int,
    val userName: String,
    val itemList: List<CartInfo>, // 주문 완료한 메뉴 리스트라 불변 리스트로 선언 (환불 기능이 없으므로)
    val orderDate: String
)
