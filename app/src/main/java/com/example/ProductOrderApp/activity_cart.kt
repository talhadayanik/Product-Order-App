package com.example.ProductOrderApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.ProductOrderApp.adapters.ProductsAdapter
import com.example.ProductOrderApp.models.CartItem
import com.example.ProductOrderApp.models.OrderItem
import com.example.ProductOrderApp.models.Product
import com.example.ProductOrderApp.services.DummyService
import com.example.ProductOrderApp.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class activity_cart : AppCompatActivity() {

    lateinit var dummyService: DummyService
    lateinit var listCart : ListView
    lateinit var btnSiparisVer : Button

    companion object{
        val cartProducts : MutableList<Product> = mutableListOf()
        val cartItems : MutableList<CartItem> = mutableListOf()
        var orderItem : OrderItem? = null
        var orderedItems : OrderCallbackData? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        dummyService = ApiClient.getClient().create(DummyService::class.java)
        listCart = findViewById(R.id.listCart)
        btnSiparisVer = findViewById(R.id.btnSiparisVer)

        val productsAdapter = ProductsAdapter(this@activity_cart, cartProducts)
        listCart.adapter = productsAdapter

        listCart.setOnItemLongClickListener { parent, view, position, id ->
            cartProducts.removeAt(id.toInt())
            productsAdapter.updateList(cartProducts)
            true
        }

        btnSiparisVer.setOnClickListener {
            for (i in cartProducts){
                cartItems.add(CartItem(i.id))
            }
            orderItem = OrderItem(1, cartItems)
            if(orderItem != null){
                dummyService.orderItems(orderItem!!).enqueue(object : Callback<OrderCallbackData> {
                    override fun onResponse(call: Call<OrderCallbackData>, response: Response<OrderCallbackData>){
                        var orderCallback : OrderCallbackData? = response.body()
                        orderedItems = orderCallback
                        println(orderCallback.toString())
                        Toast.makeText(this@activity_cart, "Sipariş verildi", Toast.LENGTH_LONG).show()
                        cartItems.clear()
                        cartProducts.clear()
                        productsAdapter.updateList(cartProducts)
                    }
                    override fun onFailure(call: Call<OrderCallbackData>, t: Throwable) {
                        Log.e("error : " , t.toString())
                        Toast.makeText(this@activity_cart, "Sipariş verilemedi", Toast.LENGTH_LONG).show()

                    }
                })
            }
        }
    }
}
