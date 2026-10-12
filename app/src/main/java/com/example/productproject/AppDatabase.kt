package com.example.productproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Product::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDAO() : ProductDAO

    companion object {
        @Volatile
        private var instance : AppDatabase? = null

        fun getAppDatabase(context : Context) : AppDatabase {
            return instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "products.db"
                ).build().also{ instance = it }
            }
        }
    }
}