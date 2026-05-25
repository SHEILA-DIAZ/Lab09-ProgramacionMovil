package com.example.lab09.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lab09.viewmodel.AmphibianViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibianHomeScreen(viewModel: AmphibianViewModel) {
    val amphibians by viewModel.amphibians.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAmphibians()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Anfibios - Sheila Diaz Rojas", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(amphibians) { amphibian ->
                AmphibianCard(amphibian = amphibian)
            }
        }
    }
}

@Composable
fun AmphibianCard(amphibian: com.example.lab09.data.model.Amphibian) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Text(
                text = "${amphibian.name} (${amphibian.type})",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold
            )
            AsyncImage(
                model = amphibian.imgSrc,
                contentDescription = amphibian.name,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = amphibian.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Desarrollado por: Sheila Diaz Rojas",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
