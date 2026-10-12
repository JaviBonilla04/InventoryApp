package com.example.productproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface ProductDAO {
    @Insert
    suspend fun insert(product : Product)

    @Delete
    suspend fun delete(product : Product)

    @Query("SELECT * FROM products ORDER BY id DESC")
    suspend fun getProductList(): List<Product>
}