package com.example.lab09.data.repository

import com.example.lab09.data.model.ProductResponse
import com.example.lab09.data.remote.ProductApiService

class ProductRepository(private val apiService: ProductApiService) {
    suspend fun getProducts(): ProductResponse {
        return apiService.getProducts()
    }
}
