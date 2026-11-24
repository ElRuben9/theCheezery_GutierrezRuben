package gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductComboEntity

@Dao
interface ProductComboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCombo(productCombo: ProductComboEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCombos(productCombos: List<ProductComboEntity>)

    @Delete
    suspend fun deleteProductCombo(productCombo: ProductComboEntity)

    @Query(value = "SELECT * FROM ProductsCombo WHERE idCombo = :comboId")
    suspend fun getProductsByCombo(comboId: Int): List<ProductComboEntity>

    @Query(value = "DELETE FROM ProductsCombo WHERE idCombo = :comboId")
    suspend fun deleteProductsFromCombo(comboId: Int)
}
