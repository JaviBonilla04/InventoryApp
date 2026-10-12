package com.example.productproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts : RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var dao : ProductDAO

    private val addProductLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> if(result.resultCode == RESULT_OK){
            loadProducts()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = AppDatabase.getAppDatabase(this).productDAO()

        rvProducts = findViewById(R.id.rvProducts)
        rvProducts.layoutManager = LinearLayoutManager(this)

        adapter = ProductAdapter(
            onDeleteClick = {
                productToDelete -> deleteProduct(productToDelete)
            }
        )
        rvProducts.adapter = adapter

        val btnAdd = findViewById<Button>(R.id.btnSave)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            addProductLauncher.launch(intent)
        }

        loadProducts()
    }

    private fun deleteProduct(product: Product) {
        lifecycleScope.launch {
            dao.delete(product)
            loadProducts()
        }
    }

    private fun loadProducts() {
        lifecycleScope.launch {
           val products = dao.getProductList()
            adapter.updateList(products)
        }
    }
}