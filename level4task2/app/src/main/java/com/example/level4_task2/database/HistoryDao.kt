package com.example.level4_task2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao interface HistoryDao {
    @Query("SELECT * FROM historyitem")
    fun getAll(): List<HistoryItem>

    @Query("SELECT * FROM historyitem WHERE result LIKE :result")
    fun findByTitle(result: String): HistoryItem

    @Insert
    fun insert(vararg historyItem: HistoryItem)

    @Query("DELETE FROM historyitem")
    fun nukeTable()

}