package com.example.lab09.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lab09.viewmodel.PostViewModel

@Composable
fun HomeScreen(navController: NavHostController, viewModel: PostViewModel) {
    val posts by viewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Publicaciones", style = MaterialTheme.typography.headlineMedium)
        LazyColumn {
            items(posts) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("detail/${post.id}") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "ID: ${post.id}", style = MaterialTheme.typography.labelSmall)
                        Text(text = post.title, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
