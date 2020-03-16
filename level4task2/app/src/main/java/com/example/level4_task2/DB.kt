package com.example.level4_task2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun HistoryDao(): DAO
}