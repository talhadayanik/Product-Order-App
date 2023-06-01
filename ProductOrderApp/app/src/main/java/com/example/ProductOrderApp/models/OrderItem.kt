package com.example.ProductOrderApp.models

data class OrderItem (
    var userId : Long = 1,
    var products : Collection<CartItem> = listOf()
)
