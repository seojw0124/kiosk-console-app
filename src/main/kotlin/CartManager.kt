import model.CartInfo
import utils.KoreanUtil
import java.text.DecimalFormat

class CartManager {

    private val cartList = mutableListOf<CartInfo>() // 장바구니 리스트는 추가, 삭제가 빈번하게 일어나므로 MutableList 사용

    fun addCartItem(item: CartInfo, categoryId: Int) {
        cartList.add(item)

        val word = if (categoryId == 1) {
            "잔"
        } else {
            "개"
        }
        println("${item.itemName} ${item.quantity}${KoreanUtil().getCompleteWordByJongsung(word)} 장바구니에 담았습니다.")
    }

    fun getLastCartItemId(): Int {
        return cartList.last().cartId
    }

    fun isCartListEmpty(): Boolean {
        return cartList.isEmpty()
    }

    fun showMyCartList() {
        var totalPrice = 0
        val decimalFormat = DecimalFormat("#,###")

        println("""
            ====================================
                        << 장바구니 >>
        """.trimIndent())
        cartList.forEach {
            totalPrice += it.price * it.quantity
            println("${it.itemName} x ${it.quantity} ................ ${decimalFormat.format(it.price * it.quantity)}원")
        }
        println("""
            
            총액: ${decimalFormat.format(totalPrice)}원
            
            1. 결제하기    0. 뒤로가기
            ====================================
        """.trimIndent())
    }
}