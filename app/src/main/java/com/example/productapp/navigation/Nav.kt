package com.example.productapp.navigation

sealed class Screen(val route: String) {
    object ProductList : Screen("product_list")
    object ProductDetails : Screen("product_details")
}
