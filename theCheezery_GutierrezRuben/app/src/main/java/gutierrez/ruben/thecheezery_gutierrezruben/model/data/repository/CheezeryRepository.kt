package gutierrez.ruben.thecheezery_gutierrezruben.model.data.repository

import androidx.room.withTransaction
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.AppDatabase
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ComboEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductComboEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.relation.ComboWithProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CheezeryRepository(private val database: AppDatabase) {

    private val productDao = database.productDao()
    private val comboDao = database.comboDao()
    private val productComboDao = database.productComboDao()

    // Productos
    suspend fun insertProduct(product: ProductEntity): Long {
        return productDao.insertProduct(product)
    }

    suspend fun insertProducts(products: List<ProductEntity>) {
        productDao.insertProducts(products)
    }

    suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    suspend fun getProductById(id: Int): ProductEntity? {
        return productDao.getProductById(productId = id)
    }

    fun searchProducts(name: String): Flow<List<ProductEntity>> {
        return productDao.searchProducts(name)
    }

    suspend fun seedProductsIfEmpty(products: List<ProductEntity>) {
        if (getAllProducts().first().isEmpty()) insertProducts(products)
    }

    // Combos
    suspend fun insertCombo(combo: ComboEntity): Long {
        return comboDao.insertCombo(combo)
    }

    suspend fun updateCombo(combo: ComboEntity) {
        comboDao.updateCombo(combo)
    }

    suspend fun deleteCombo(combo: ComboEntity) {
        comboDao.deleteCombo(combo)
    }

    fun getAllCombos(): Flow<List<ComboEntity>> {
        return comboDao.getAllCombos()
    }

    suspend fun getComboById(id: Int): ComboEntity? {
        return comboDao.getComboById(comboId = id)
    }

    fun searchCombos(name: String): Flow<List<ComboEntity>> {
        return comboDao.searchCombos(name)
    }

    fun getCombosWithProducts(): Flow<List<ComboWithProducts>> {
        return comboDao.getCombosWithProducts()
    }

    // Relación Combo-Producto
    suspend fun insertProductCombo(productCombo: ProductComboEntity): Long {
        return productComboDao.insertProductCombo(productCombo)
    }

    suspend fun deleteProductsFromCombo(comboId: Int) {
        productComboDao.deleteProductsFromCombo(comboId)
    }

    suspend fun saveCombo(name: String, price: Float, productIds: List<Int>): Long {
        return database.withTransaction {
            val comboId = comboDao.insertCombo(
                ComboEntity(name = name.trim(), price = price)
            )
            productComboDao.insertProductCombos(
                productIds.distinct().map { productId ->
                    ProductComboEntity(productId = productId, comboId = comboId.toInt())
                }
            )
            comboId
        }
    }
}
