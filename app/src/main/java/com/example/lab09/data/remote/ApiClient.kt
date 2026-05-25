package com.example.lab09.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val POSTS_BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val AMPHIBIANS_BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"
    private const val PRODUCTS_BASE_URL = "https://dummyjson.com/"

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val postApiService: PostApiService by lazy {
        createRetrofit(POSTS_BASE_URL).create(PostApiService::class.java)
    }

    val amphibianApiService: AmphibianApiService by lazy {
        createRetrofit(AMPHIBIANS_BASE_URL).create(AmphibianApiService::class.java)
    }

    val productApiService: ProductApiService by lazy {
        createRetrofit(PRODUCTS_BASE_URL).create(ProductApiService::class.java)
    }
}
