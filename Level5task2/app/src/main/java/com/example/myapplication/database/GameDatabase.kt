package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Game::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {

    abstract fun noteDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "NOTEPAD_DATABASE"

        @Volatile
        private var itemDatabaseInstance: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase? {
            if (itemDatabaseInstance == null) {
                synchronized(GameDatabase::class.java) {
                    if (itemDatabaseInstance == null) {
                        itemDatabaseInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                GameDatabase::class.java,
                                DATABASE_NAME
                            ).addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    itemDatabaseInstance?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.noteDao()
                                        }
                                    }
                                }
                            }).fallbackToDestructiveMigration().build()
                    }
                }
            }

            return itemDatabaseInstance
        }
    }

}