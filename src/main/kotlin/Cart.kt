import model.CartItem
import utils.KoreanUtil
import java.text.DecimalFormat

class Cart {

    private val cartList = mutableListOf<CartItem>() // 장바구니 리스트는 추가, 삭제가 빈번하게 일어나므로 MutableList 사용

    fun addCartItem(item: AbstractMenu, quantity: Int) {
        val cartItem = CartItem(
            item.itemId,
            item.name,
            item.price,
            quantity,
        )
        cartList.add(cartItem)

        val word = if (item.categoryId == 1) {
            "잔"
        } else {
            "개"
        }
        println("${item.name} ${quantity}${KoreanUtil().getCompleteWordByJongsung(word)} 장바구니에 담았습니다.")
    }

    fun showCartList() {
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