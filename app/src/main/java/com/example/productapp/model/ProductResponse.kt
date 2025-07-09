package com.example.productapp.model

data class ProductResponse(
    val header: Header,
    val filters: List<String>,
    val products: List<Product>
)
