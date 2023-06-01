package com.example.ProductOrderApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.ProductOrderApp.adapters.OrdersItemAdapter
import com.example.ProductOrderApp.services.DummyService
import com.example.ProductOrderApp.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class activity_orders : AppCompatActivity() {

    lateinit var dummyService : DummyService
    lateinit var listOrders : ListView
    lateinit var orders : MutableList<CartOrderProduct>

    var policy : StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        StrictMode.setThreadPolicy(policy)

        dummyService = ApiClient.getClient().create(DummyService::class.java)
        listOrders = findViewById(R.id.listOrders)
        orders = mutableListOf()

        val ordersAdapter = OrdersItemAdapter(this@activity_orders, orders)
        listOrders.adapter = ordersAdapter

        dummyService.getOrders().enqueue(object :
            Callback<OrderCallbackData> {
            override fun onResponse(call: Call<OrderCallbackData>, response: Response<OrderCallbackData>){
                var orderCallback : OrderCallbackData? = response.body()
                if (orderCallback != null) {
                    orders = (orderCallback.products).toMutableList()
                    ordersAdapter.updateList(orders)
                }
                println(orderCallback.toString())
            }
            override fun onFailure(call: Call<OrderCallbackData>, t: Throwable) {
                Log.e("error : " , t.toString())
                Toast.makeText(this@activity_orders, "Siparişler alınamadı", Toast.LENGTH_LONG).show()
            }
        })
    }
}