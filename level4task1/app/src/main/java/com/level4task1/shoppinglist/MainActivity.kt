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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val shoppingList = arrayListOf<Item>()
    private val productAdapter = Adapter(shoppingList)

    private lateinit var itemRepo: ItemRepo
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Shopping List Kotlin"

        setSupportActionBar(toolbar)

        itemRepo = ItemRepo(this)
        initViews()
    }

    private fun initViews() {
        rvShoppingList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvShoppingList.adapter = productAdapter
        rvShoppingList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvShoppingList)
        getShoppingListFromDatabase()

        fab.setOnClickListener { addProduct() }
    }

    private fun getShoppingListFromDatabase() {
        mainScope.launch {
            val shoppingList = withContext(Dispatchers.IO) {
                itemRepo.getAllProducts()
            }
            this@MainActivity.shoppingList.clear()
            this@MainActivity.shoppingList.addAll(shoppingList)
            this@MainActivity.productAdapter.notifyDataSetChanged()
        }
    }

    private fun validateFields(): Boolean {
        return if (etProduct.text.toString().isNotBlank() && etQuantity.text.toString().isNotBlank()) {
            true
        } else {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
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
                val position = viewHolder.adapterPosition

                val productToDelete = shoppingList[position]

                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        itemRepo.deleteProduct(productToDelete)
                    }
                    getShoppingListFromDatabase()
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
            getShoppingListFromDatabase()
        }
    }


    private fun addProduct() {
        if (validateFields()) {
            mainScope.launch {
                val product = Item(
                    name = etProduct.text.toString(),
                    quantity = etQuantity.text.toString().toInt()
                )

                withContext(Dispatchers.IO) {
                    itemRepo.insertProduct(product)
                }

                getShoppingListFromDatabase()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete_shopping_list -> {
                deleteItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
