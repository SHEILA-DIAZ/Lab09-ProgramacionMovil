package com.example.lab09.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab09.viewmodel.PostViewModel

@Composable
fun FavoritesScreen(
    viewModel: PostViewModel
) {
    val favorites by viewModel.favorites.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(favorites) { post ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "ID: ${post.id}", style = MaterialTheme.typography.labelSmall)
                    Text(text = post.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = post.body, style = MaterialTheme.typography.bodySmall)
                    Button(
                        onClick = {
                            viewModel.deleteFavorite(post.id)
                        },
                        modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
                    ) {
                        Text("Eliminar favorito")
                    }
                    Text(
                        text = "Sheila Diaz Rojas",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(top = 4.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
