package com.example.level4_task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.*


class History : AppCompatActivity() {

    private val items = arrayListOf<HistoryItem>()
    private val adapter = Adapter(items)
    lateinit var historyRepo: HistoryRepo
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        historyRepo = HistoryRepo(this)

        setSupportActionBar(history_toolbar)

        supportActionBar?.title = "History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_history -> {
                clearHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    private fun getItemsFromDatabase() {
        mainScope.launch {
            val items = withContext(Dispatchers.IO) {
                historyRepo.getAll()
            }

            this@History.items.clear()
            this@History.items.addAll(items)
            this@History.adapter.notifyDataSetChanged()
        }
    }

    fun initViews() {
        getItemsFromDatabase()
        r_history.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        r_history.adapter = adapter
    }

    fun clearHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                historyRepo.deleteAllProducts()
            }

            getItemsFromDatabase()
        }
    }
}
