import model.OrderItem
import java.text.DecimalFormat

class Order {

    private val orderList = mutableListOf<OrderItem>()

    fun addOrderItem(item: OrderItem) {
        orderList.add(item)
    }

    fun showMyOrderList(id: Int) {
        val order = orderList.find { it.orderId == id }

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