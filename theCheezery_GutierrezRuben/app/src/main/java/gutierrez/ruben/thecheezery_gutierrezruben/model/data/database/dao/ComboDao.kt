package gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ComboEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.relation.ComboWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface ComboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCombo(combo: ComboEntity): Long

    @Update
    suspend fun updateCombo(combo: ComboEntity)

    @Delete
    suspend fun deleteCombo(combo: ComboEntity)

    @Query(value = "SELECT * FROM Combos")
    fun getAllCombos(): Flow<List<ComboEntity>>

    @Query(value = "SELECT * FROM Combos WHERE idCombo = :comboId")
    suspend fun getComboById(comboId: Int): ComboEntity?

    @Query(value = "SELECT * FROM Combos WHERE nameCombo LIKE '%' || :name || '%'")
    fun searchCombos(name: String): Flow<List<ComboEntity>>

    @Transaction
    @Query(value = "SELECT * FROM Combos")
    fun getCombosWithProducts(): Flow<List<ComboWithProducts>>
}
