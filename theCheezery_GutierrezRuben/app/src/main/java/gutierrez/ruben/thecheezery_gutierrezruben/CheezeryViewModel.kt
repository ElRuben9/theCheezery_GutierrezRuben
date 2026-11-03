package gutierrez.ruben.thecheezery_gutierrezruben

import androidx.lifecycle.ViewModel

class CheezeryViewModel : ViewModel() {
    var cart: List<Product> = emptyList()
        private set

    fun addToCart(product: Product) {
        cart = cart + product
    }

    fun removeFromCart(index: Int) {
        if (index in cart.indices) cart = cart.toMutableList().also { it.removeAt(index) }
    }

    fun total(): Double = cart.sumOf { it.price }
}
