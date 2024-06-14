import model.OrderInfo
import java.text.DecimalFormat

class OrderManager {

    private val orderList = mutableListOf<OrderInfo>()

    fun addOrderItem(list: OrderInfo) {
        orderList.add(list)
    }

    fun isOrderListEmpty(): Boolean {
        return orderList.isEmpty()
    }

    fun getLastOrderItemId(): Int {
        return orderList.last().orderId
    }

    fun showMyOrderList(id: Int) {
        val order = orderList.find { it.userId == id }

        var totalPrice = 0
        val decimalFormat = DecimalFormat("#,###")

        if (order != null) {
            println("""
            ====================================
                        << 주문 내역 >>
            구매자: ${order.userName}
            결제일: ${order.orderDate}
        """.trimIndent())
            order.itemList.forEach {
                totalPrice += it.price * it.quantity
                println("${it.itemName} x ${it.quantity} ................ ${decimalFormat.format(it.price * it.quantity)}원")
            }
            println("""
            
            총액: ${decimalFormat.format(totalPrice)}원
            
            0. 뒤로가기
            ====================================
        """.trimIndent())
        }
    }
}