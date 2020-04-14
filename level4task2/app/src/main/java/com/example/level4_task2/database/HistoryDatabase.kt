package com.example.level4_task2

import android.content.Context
import androidx.room.*
import com.example.level4_task2.database.Converters

//
//val MIGRATION_1_2 = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE historyitem ADD COLUMN 'date' STRING")
//    }
//}

@Database(entities = [HistoryItem::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        private const val DATABASE_NAME = "HISTORY_DATABASE"

        @Volatile
        private var historyDatabaseInstance: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase? {
            if (historyDatabaseInstance == null) {
                synchronized(HistoryDatabase::class.java) {
                    if (historyDatabaseInstance == null) {
                        historyDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                HistoryDatabase::class.java,
                                DATABASE_NAME
                            ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return historyDatabaseInstance
        }
    }

}