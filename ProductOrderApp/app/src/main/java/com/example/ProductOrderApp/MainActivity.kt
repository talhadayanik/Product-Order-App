package com.example.ProductOrderApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import com.example.ProductOrderApp.adapters.ProductsAdapter
import com.example.ProductOrderApp.utils.ApiClient
import com.example.ProductOrderApp.models.Product
import com.example.ProductOrderApp.models.ProductList
import com.example.ProductOrderApp.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var dummyService: DummyService
    lateinit var txtSearch : EditText
    lateinit var btnSearch : ImageButton
    lateinit var btnSiparislerim : Button
    lateinit var btnSepetim : Button
    lateinit var listProducts : ListView
    lateinit var products : MutableList<Product>

    var policy : StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()

    companion object{
        lateinit var selectedProduct : Product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StrictMode.setThreadPolicy(policy)

        dummyService = ApiClient.getClient().create(DummyService::class.java)
        txtSearch = findViewById(R.id.txtSearch)
        btnSearch = findViewById(R.id.btnSearch)
        btnSepetim = findViewById(R.id.btnSepetim)
        btnSiparislerim = findViewById(R.id.btnSiparisVer)
        listProducts = findViewById(R.id.listProducts)
        products = mutableListOf()

        val productsAdapter = ProductsAdapter(this@MainActivity, products)
        listProducts.adapter = productsAdapter

        dummyService.getFirstTen(10).enqueue(object : Callback<ProductList>{
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>){
                if(response.body() != null){
                    products.clear()
                    (response.body()!!.plist as Collection<Product>?)?.let { products.addAll(it) }
                    productsAdapter.notifyDataSetChanged()

                }
            }
            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.e("error : " , t.toString())
                Toast.makeText(this@MainActivity, "Failed to fetch products", Toast.LENGTH_LONG).show()

            }
        })

        listProducts.setOnItemClickListener { parent, view, position, id ->
            selectedProduct = products.get(id.toInt())
            var intent = Intent(this, activity_details::class.java)
            startActivity(intent)
        }

        btnSearch.setOnClickListener {
            if(txtSearch.text.toString() != ""){
                dummyService.getSearched(txtSearch.text.toString()).enqueue(object : Callback<ProductList>{
                    override fun onResponse(call: Call<ProductList>, response: Response<ProductList>){
                        if(response.body() != null){
                            products.clear()
                            (response.body()!!.plist as Collection<Product>?)?.let { products.addAll(it) }
                            productsAdapter.notifyDataSetChanged()
                        }
                    }
                    override fun onFailure(call: Call<ProductList>, t: Throwable) {
                        Log.e("error : " , t.toString())
                        Toast.makeText(this@MainActivity, "Failed to fetch products", Toast.LENGTH_LONG).show()

                    }
                })
            }
        }

        btnSepetim.setOnClickListener {
            var i = Intent(this@MainActivity, activity_cart::class.java)
            startActivity(i)
        }

        btnSiparislerim.setOnClickListener {
            var i = Intent(this@MainActivity, activity_orders::class.java)
            startActivity(i)
        }
    }
}