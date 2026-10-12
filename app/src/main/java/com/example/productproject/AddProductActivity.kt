package com.example.productproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val etName = findViewById<EditText>(R.id.etName)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val etStock = findViewById<EditText>(R.id.etStock)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val dao  = AppDatabase.getAppDatabase(this).productDAO()

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val price = etPrice.text.toString().trim().toDoubleOrNull()
            val availableStock = etStock.text.toString().trim().toLongOrNull()

            if(name.isEmpty() || price == null || availableStock == null) {
                Toast.makeText(this, "All fields are necessay", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                dao.insert(Product(name = name, price = price, availableStock = availableStock))
                setResult(RESULT_OK)
                finish()
            }
        }

    }

}