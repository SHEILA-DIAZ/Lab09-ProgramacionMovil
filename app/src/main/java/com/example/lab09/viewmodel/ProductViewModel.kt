package com.example.lab09.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab09.data.model.ProductModel
import com.example.lab09.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> = _products

    private val _selectedProduct = MutableStateFlow<ProductModel?>(null)
    val selectedProduct: StateFlow<ProductModel?> = _selectedProduct

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getProducts()
                _products.value = response.products
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectProduct(product: ProductModel) {
        _selectedProduct.value = product
    }
}
