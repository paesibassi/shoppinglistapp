package com.paesibassi.shoppinglistapp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [Item::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 3, to = 4, spec = ItemsDatabase.MigrateItemDoneToItemCompleteColumn::class),
    ],
    exportSchema = true
)
abstract class ItemsDatabase : RoomDatabase() {
    abstract val itemsDao: ItemsDao

    @RenameColumn(tableName = "items_table", fromColumnName = "done", toColumnName = "complete")
    class MigrateItemDoneToItemCompleteColumn : AutoMigrationSpec

    companion object {
        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getInstance(context: Context): ItemsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemsDatabase::class.java,
                        "items_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
