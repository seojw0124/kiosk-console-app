import model.CartInfo
import model.UserInfo
import utils.FormatUtil
import utils.KoreanUtil
import java.text.DecimalFormat

class CartManager {

    private val cart = mutableListOf<CartInfo>() // 장바구니 리스트는 추가, 삭제가 빈번하게 일어나므로 MutableList 사용
    private var totalPrice = 0

    fun addCartItem(item: CartInfo, categoryId: Int) {
        val sameCartItem = cart.find { it.itemName == item.itemName }
        if (sameCartItem != null) {
            sameCartItem.quantity += item.quantity
        } else {
            cart.add(item)
        }

        val word = if (categoryId == 1) {
            "잔"
        } else {
            "개"
        }
        println("${item.itemName} ${item.quantity}${KoreanUtil().getCompleteWordByJongsung(word)} 장바구니에 담았습니다.")
        println(cart)
    }

    fun getLastCartItemId(): Int {
        return cart.last().cartId
    }

    fun isCartEmpty(): Boolean {
        return cart.isEmpty()
    }

    fun getMyCartItemList(): List<CartInfo> {
        return cart
    }

    fun clearMyCart() {
        cart.clear()
    }

    fun getCartItemTotalPrice(): Int {
        cart.forEach {
            totalPrice += it.price * it.quantity
        }
        return totalPrice
    }

    fun showMyCart(money: Int) {
        totalPrice = 0
        println(
            """
            ====================================
                        << 장바구니 >>
        """.trimIndent()
        )
        cart.forEach {
            totalPrice += it.price * it.quantity
            println("${it.cartId}. ${it.itemName} x ${it.quantity} ............. ${FormatUtil().decimalFormat(it.price * it.quantity)}원")
        }
        println(
            """
            
            총액: ${FormatUtil().decimalFormat(totalPrice)}원
            잔액: ${FormatUtil().decimalFormat(money)}원
            
            1. 결제하기    0. 뒤로가기
            ====================================
        """.trimIndent()
        )
    }
}