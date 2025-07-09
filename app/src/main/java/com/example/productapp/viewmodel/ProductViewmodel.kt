package com.example.productapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.data.ProductService
import com.example.productapp.model.Product
import com.example.productapp.model.ProductResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


enum class ProductFilter(val key: String) {
    ALL("Alle"),
    AVAILABLE("Verfügbar"),
    FAVORITED("Vorgemerkt");
    //LESSTHAN1000("<1000Euro");

    companion object {
        fun fromKey(key: String): ProductFilter {
            return entries.firstOrNull { it.key.equals(key, ignoreCase = true) } ?: ALL
        }
    }
}

class ProductListViewModel : ViewModel() {
    // Private state (Viewmodel thay đổi)
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _filters = MutableStateFlow<List<String>>(emptyList())
    private val _selectedFilter = MutableStateFlow(ProductFilter.ALL.key)
    private val _headerTitle = MutableStateFlow("")
    private val _headerSubtitle = MutableStateFlow("")

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    private val _favoriteProducts = MutableStateFlow<Set<Product>>(emptySet())

    private val _isRefreshing = MutableStateFlow(false)
    private val _hasError = MutableStateFlow(false)

    private val filterTranslations = mapOf(
        "Alle" to "All",
        "Verfügbar" to "Available",
        "Vorgemerkt" to "Favorited"
    )

    // Public State (UI đọc)
    val selectedProduct: StateFlow<Product?> = _selectedProduct
    val favoriteProducts: StateFlow<Set<Product>> = _favoriteProducts
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    val hasError: StateFlow<Boolean> = _hasError
    val filters: StateFlow<List<String>> = _filters
    val selectedFilter: StateFlow<String> = _selectedFilter
    val headerTitle: StateFlow<String> = _headerTitle
    val headerSubtitle: StateFlow<String> = _headerSubtitle

    // Products filtered by the current selected filter
    val filteredProducts: StateFlow<List<Product>> = combine(
        _allProducts,
        _selectedFilter,
        _favoriteProducts
    ) { products, selectedKey, favorites ->
        when (ProductFilter.fromKey(selectedKey)) {
            ProductFilter.ALL -> products
            ProductFilter.AVAILABLE -> products.filter { it.available }
            ProductFilter.FAVORITED -> products.filter { it in favorites }
            //ProductFilter.LESSTHAN1000 -> products.filter {it.price.value <1000}
        }
    }.stateIn(scope=viewModelScope, started=SharingStarted.WhileSubscribed(5000), initialValue = emptyList())

    private var requestCount = 1

    // Logic UI
    // Sets the currently selected filter
    fun setFilter(filter: String) {
        _selectedFilter.value = filter
    }

    // Loads products from the API and updates UI state
    fun loadProducts() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                if (requestCount ++% 4 == 0) {
                    throw Exception("Simulated API error")
                }
                val response = fetchProducts() // gọi API
                updateStates(response)
                _hasError.value = false
            } catch (e: Exception) { //bắt lỗi
                _allProducts.value = emptyList()
                _hasError.value = true
            } finally { // chờ để hiện loading
                kotlinx.coroutines.delay(800)
                _isRefreshing.value = false
            }
        }
    }


    // Selects Product
    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    // Toggles favorite
    fun toggleFavorite(product: Product) {
        _favoriteProducts.update { current ->
            current.toMutableSet().apply {
                if (!add(product)) remove(product)
            }
        }
    }
    fun translateFilter(filter: String): String {
        return filterTranslations[filter] ?: filter
    }

    // Fetches products from API (repository)
    private suspend fun fetchProducts(): ProductResponse {
        return ProductService.api.getProducts()
    }

    // Updates all UI states based on the API response
    private fun updateStates(response: ProductResponse) {
        _allProducts.value = response.products
        _filters.value = response.filters
        _headerTitle.value = response.header.headerTitle
        _headerSubtitle.value = response.header.headerDescription
        /* tạo 1 list, thêm filter mới cho
        val extendedFilters = buildList {
            addAll(response.filters)
            if (!contains(ProductFilter.LESSTHAN1000.key)) {
                add(ProductFilter.LESSTHAN1000.key)
            }
        }
        _filters.value= extendedFilters
         */

    }

}

