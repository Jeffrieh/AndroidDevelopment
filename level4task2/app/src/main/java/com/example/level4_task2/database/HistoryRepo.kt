package com.example.level4_task2

import android.content.Context

class HistoryRepo(context: Context) {

    private val historyDao: HistoryDao

    init {
        val database = HistoryDatabase.getDatabase(context)
        historyDao = database!!.historyDao()
    }

    fun getAll(): List<HistoryItem> = historyDao.getAll()
    fun insert(item: HistoryItem) = historyDao.insert(item)
    fun deleteAllProducts() = historyDao.nukeTable()

}