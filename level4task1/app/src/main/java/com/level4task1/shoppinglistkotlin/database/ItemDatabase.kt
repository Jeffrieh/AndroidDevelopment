package com.level4task1.shoppinglistkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.level4task1.shoppinglistkotlin.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private const val DATABASE_NAME = "SHOPPING_LIST_DATABASE"

        @Volatile
        private var itemDatabaseInstance: ItemDatabase? = null

        fun getDatabase(context: Context): ItemDatabase? {
            if (itemDatabaseInstance == null) {
                synchronized(ItemDatabase::class.java) {
                    if (itemDatabaseInstance == null) {
                        itemDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                ItemDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return itemDatabaseInstance
        }
    }

}