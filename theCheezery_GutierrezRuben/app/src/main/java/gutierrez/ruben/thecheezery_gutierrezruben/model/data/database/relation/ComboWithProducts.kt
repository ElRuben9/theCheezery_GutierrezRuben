package gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ComboEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductComboEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductEntity

data class ComboWithProducts(
    @Embedded
    val combo: ComboEntity,

    @Relation(
        parentColumn = "idCombo",
        entityColumn = "idProduct",
        associateBy = Junction(
            value = ProductComboEntity::class,
            parentColumn = "idCombo",
            entityColumn = "idProduct"
        )
    )
    val products: List<ProductEntity>
)
