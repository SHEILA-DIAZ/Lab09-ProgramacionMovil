package com.example.lab09.data.remote

import com.example.lab09.data.model.ProductResponse
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}
