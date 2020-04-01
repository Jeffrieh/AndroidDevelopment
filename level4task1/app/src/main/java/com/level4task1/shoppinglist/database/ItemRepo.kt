package com.level4task1.shoppinglist.database

import android.content.Context
import com.level4task1.shoppinglist.Item

class ItemRepo(context: Context) {

    private val productDao: ProductDao

    init {
        val database =
            ItemDatabase.getDatabase(context)
        productDao = database!!.productDao()
    }

    suspend fun getAllProducts(): List<Item> = productDao.getAllProducts()
    suspend fun insertProduct(item: Item) = productDao.insertProduct(item)
    suspend fun deleteProduct(item: Item) = productDao.deleteProduct(item)
    suspend fun deleteAllProducts() = productDao.deleteAllProducts()
}