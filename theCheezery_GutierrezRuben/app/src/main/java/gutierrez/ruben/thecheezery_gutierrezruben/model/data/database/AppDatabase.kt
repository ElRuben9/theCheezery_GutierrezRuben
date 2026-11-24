package gutierrez.ruben.thecheezery_gutierrezruben.model.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.dao.ComboDao
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.dao.ProductComboDao
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.dao.ProductDao
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ComboEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductComboEntity
import gutierrez.ruben.thecheezery_gutierrezruben.model.data.database.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        ComboEntity::class,
        ProductComboEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun comboDao(): ComboDao
    abstract fun productComboDao(): ProductComboDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cheezery.db"
                ).fallbackToDestructiveMigration(dropAllTables = true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
