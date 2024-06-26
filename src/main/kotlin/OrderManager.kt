import model.OrderInfo
import utils.FormatUtil

class OrderManager {

    private var order: OrderInfo? = null

    fun addItem(item: OrderInfo) {
        order = item
    }

    fun isEmpty(): Boolean {
        return order == null
    }

    fun getLastItemId(): Int {
        return order!!.orderId
    }

    fun showReceipt() {
        var totalPrice = 0

        println(
            """
        **************************************
                 << 주문 번호: ${order!!.orderId} >>
        결제일: ${order!!.orderDate}
        
        """.trimIndent()
        )
        order!!.itemList.forEach {
            totalPrice += it.price * it.quantity
            println("${it.cartId}. ${it.itemName} x ${it.quantity} ............. ${FormatUtil().decimalFormat(it.price * it.quantity)}원")
        }
        println(
            """
        
        총액: ${FormatUtil().decimalFormat(totalPrice)}원
        
        0. 뒤로가기
        **************************************
        """.trimIndent()
        )
    }
}
