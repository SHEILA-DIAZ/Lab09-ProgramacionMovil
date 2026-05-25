package com.example.lab09.data.repository

import com.example.lab09.data.model.Amphibian
import com.example.lab09.data.remote.AmphibianApiService

class AmphibianRepository(private val apiService: AmphibianApiService) {
    suspend fun getAmphibians(): List<Amphibian> {
        return apiService.getAmphibians()
    }
}
