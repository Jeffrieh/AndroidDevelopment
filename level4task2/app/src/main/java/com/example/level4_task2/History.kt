package com.example.level4_task2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_history.*

class History : AppCompatActivity() {


    lateinit var db : AppDatabase;
    lateinit var historyAdapter: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "HistoryItem.db"
        ).allowMainThreadQueries().build()

        var list = db.HistoryDao().getAll()

        r_history.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        historyAdapter= Adapter(list)
        r_history.adapter = historyAdapter

        clear.setOnClickListener({clearHistory()})
    }

    fun clearHistory(){
        db.HistoryDao().nukeTable()
        historyAdapter = Adapter(emptyList())
        historyAdapter.notifyDataSetChanged()
    }
}
