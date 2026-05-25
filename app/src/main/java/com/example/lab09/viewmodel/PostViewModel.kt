package com.example.lab09.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab09.data.local.PostEntity
import com.example.lab09.data.model.PostModel
import com.example.lab09.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {
    private val _posts = MutableStateFlow<List<PostModel>>(emptyList())
    val posts: StateFlow<List<PostModel>> = _posts

    private val _selectedPost = MutableStateFlow<PostModel?>(null)
    val selectedPost: StateFlow<PostModel?> = _selectedPost

    private val _favorites = MutableStateFlow<List<PostEntity>>(emptyList())
    val favorites: StateFlow<List<PostEntity>> = _favorites

    fun loadPosts() {
        viewModelScope.launch {
            try {
                _posts.value = repository.getPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadPostById(id: Int) {
        viewModelScope.launch {
            try {
                _selectedPost.value = repository.getPostById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveFavorite(post: PostModel) {
        viewModelScope.launch {
            try {
                repository.saveFavorite(post)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavorites().collect {
                _favorites.value = it
            }
        }
    }

    fun deleteFavorite(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteFavorite(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
