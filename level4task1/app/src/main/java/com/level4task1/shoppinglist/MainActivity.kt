package com.level4task1.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.level4task1.shoppinglist.database.ItemRepo

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val shoppingList = arrayListOf<Item>()
    private val productAdapter = Adapter(shoppingList)

    private lateinit var itemRepo: ItemRepo
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        itemRepo = ItemRepo(this)
        initViews()
    }

    private fun initViews() {
        rvShoppingList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvShoppingList.adapter = productAdapter

        rvShoppingList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvShoppingList)
        fetchItems()

        fab.setOnClickListener { addItems() }
    }

    private fun fetchItems() {
        mainScope.launch {
            val shoppingList = withContext(Dispatchers.IO) {
                itemRepo.getAllProducts()
            }
            this@MainActivity.shoppingList.clear()
            this@MainActivity.shoppingList.addAll(shoppingList)
            this@MainActivity.productAdapter.notifyDataSetChanged()
        }
    }

    private fun validate(): Boolean {
        return if (inItem.text.toString().isBlank() && inQuantity.text.toString().isBlank()) {
            Toast.makeText(this, "Fill in every field.", Toast.LENGTH_SHORT).show()
            false
        } else true

    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        itemRepo.deleteProduct(shoppingList[viewHolder.adapterPosition])
                        fetchItems()
                    }
                }
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun deleteItems() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                itemRepo.deleteAllProducts()
            }
            fetchItems()
        }
    }

    private fun addItems() {
        if (validate()) {
            mainScope.launch {
                val product = Item(
                    name = inItem.text.toString(),
                    quantity = inQuantity.text.toString().toInt()
                )

                withContext(Dispatchers.IO) {
                    itemRepo.insertProduct(product)
                }

                fetchItems()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_shopping_list -> {
                deleteItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
