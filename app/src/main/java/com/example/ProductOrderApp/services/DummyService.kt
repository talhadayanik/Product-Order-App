package com.example.ProductOrderApp.services

import com.example.ProductOrderApp.OrderCallbackData
import com.example.ProductOrderApp.models.OrderItem
import com.example.ProductOrderApp.models.ProductList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DummyService {

    @GET("products")
    fun getFirstTen(@Query("limit") limit:Int) : Call<ProductList>

    @GET("products/search")
    fun getSearched(@Query("q") q: String) : Call<ProductList>

    @POST("carts/add")
    fun orderItems(@Body orderItem: OrderItem) : Call<OrderCallbackData>

    @GET("carts/1")
    fun getOrders() : Call<OrderCallbackData>
}