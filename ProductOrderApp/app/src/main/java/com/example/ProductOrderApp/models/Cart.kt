package com.example.ProductOrderApp

data class OrderCallbackData (
    val id: Long,
    val products: List<CartOrderProduct>,
    val total: Long,
    val discountedTotal: Long,
    val userId: Long,
    val totalProducts: Long,
    val totalQuantity: Long
)

data class CartOrderProduct (
    val id: Long,
    val title: String,
    val price: Long,
    val quantity: Long,
    val total: Long,
    val discountPercentage: Double,
    val discountedPrice: Long
)
