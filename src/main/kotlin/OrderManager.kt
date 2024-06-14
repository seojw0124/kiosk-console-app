import model.OrderInfo
import utils.FormatUtil
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

        if (order != null) {
            println("""
            ====================================
                        << 주문 내역 >>
            구매자: ${order.userName}
            결제일: ${order.orderDate}
            
        """.trimIndent())
            order.itemList.forEach {
                totalPrice += it.price * it.quantity
                println("${it.itemName} x ${it.quantity} ................ ${FormatUtil().decimalFormat(it.price * it.quantity)}원")
            }
            println("""
            
            총액: ${FormatUtil().decimalFormat(totalPrice)}원
            
            0. 뒤로가기
            ====================================
        """.trimIndent())
        }
    }
}