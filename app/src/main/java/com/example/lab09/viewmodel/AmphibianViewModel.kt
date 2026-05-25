package com.example.lab09.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab09.data.model.Amphibian
import com.example.lab09.data.repository.AmphibianRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AmphibianViewModel(private val repository: AmphibianRepository) : ViewModel() {
    private val _amphibians = MutableStateFlow<List<Amphibian>>(emptyList())
    val amphibians: StateFlow<List<Amphibian>> = _amphibians

    fun getAmphibians() {
        viewModelScope.launch {
            try {
                _amphibians.value = repository.getAmphibians()
            } catch (e: Exception) {
                _amphibians.value = emptyList()
            }
        }
    }
}
