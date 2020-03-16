package com.level4task1.shoppinglist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.level4task1.shoppinglist.Item

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<Item>

    @Insert
    suspend fun insertProduct(item: Item)

    @Delete
    suspend fun deleteProduct(item: Item)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()

}