package com.example.lab09.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab09.viewmodel.PostViewModel

@Composable
fun PostDetailScreen(id: Int, viewModel: PostViewModel) {
    val post by viewModel.selectedPost.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadPostById(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (post == null) {
            Text(text = "Cargando publicación...")
        } else {
            OutlinedTextField(
                value = post!!.id.toString(),
                onValueChange = {},
                label = { Text("ID") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = post!!.userId.toString(),
                onValueChange = {},
                label = { Text("User ID") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = post!!.title,
                onValueChange = {},
                label = { Text("Título") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = post!!.body,
                onValueChange = {},
                label = { Text("Contenido") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.saveFavorite(post!!) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar en favoritos")
            }
        }
    }
}
