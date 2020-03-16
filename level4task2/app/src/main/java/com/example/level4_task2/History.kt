package com.example.level4_task2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class History : AppCompatActivity() {

    lateinit var db: AppDatabase

    private val items = arrayListOf<HistoryItem>()
    private val adapter = Adapter(items)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "HistoryItem.db"
        ).allowMainThreadQueries().build()

        initViews()
    }

    private fun getItemsFromDatabase() {
        var itemList = db.HistoryDao().getAll()

        this@History.items.clear()
        this@History.items.addAll(itemList)
        this@History.adapter.notifyDataSetChanged()
    }

    fun initViews() {
        getItemsFromDatabase()

        clear.setOnClickListener({ clearHistory() })
        r_history.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        r_history.adapter = adapter

    }

    fun clearHistory() {
        db.HistoryDao().nukeTable()
        getItemsFromDatabase()
    }
}
