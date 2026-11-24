package gutierrez.ruben.thecheezery_gutierrezruben

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.AppDatabase
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.relation.ComboWithProducts
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.repository.CheezeryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CheezeryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CheezeryRepository(AppDatabase.getInstance(application))

    val products: StateFlow<List<ProductEntity>> = repository.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val combos: StateFlow<List<ComboWithProducts>> = repository.getCombosWithProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    var cart: List<ProductEntity> = emptyList()
        private set

    init {
        viewModelScope.launch {
            repository.seedProductsIfEmpty(
                ProductCatalog.products.map { product ->
                    ProductEntity(
                        name = product.name,
                        price = product.price.toFloat(),
                        image = product.imageName
                    )
                }
            )
        }
    }

    fun addToCart(product: ProductEntity) {
        cart = cart + product
    }

    fun addToCart(products: List<ProductEntity>) {
        cart = cart + products
    }

    fun removeFromCart(index: Int) {
        if (index in cart.indices) cart = cart.toMutableList().also { it.removeAt(index) }
    }

    fun total(): Float = cart.sumOf { it.price.toDouble() }.toFloat()

    fun categoryOf(product: ProductEntity): String {
        return ProductCatalog.products.firstOrNull { it.name == product.name }?.category ?: "Hot drinks"
    }

    fun saveCombo(name: String, price: Float, productIds: List<Int>, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val saved = try {
                if (name.isBlank() || price <= 0f || productIds.isEmpty()) {
                    false
                } else {
                    repository.saveCombo(name, price, productIds)
                    true
                }
            } catch (_: Exception) {
                false
            }
            onResult(saved)
        }
    }
}
