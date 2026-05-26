package com.example.lab09.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lab09.data.model.Amphibian
import com.example.lab09.viewmodel.AmphibianViewModel

@Composable
fun AmphibianHomeScreen(viewModel: AmphibianViewModel) {
    val amphibians by viewModel.amphibians.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAmphibians()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0B2010), Color(0xFF1B4332)) // Jungle Green
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp, start = 16.dp, end = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    text = "Mundo Anfibio",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color(0xFFD8F3DC),
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Sheila Diaz Rojas",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF95D5B2)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(amphibians) { amphibian ->
                AmphibianPremiumCard(amphibian = amphibian)
            }
        }
    }
}

@Composable
fun AmphibianPremiumCard(amphibian: Amphibian) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF081C15))
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                AsyncImage(
                    model = amphibian.imgSrc,
                    contentDescription = amphibian.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Overlay para legibilidad
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                startY = 300f
                            )
                        )
                )

                // Badge Brillante
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd),
                    color = Color(0xFF74C69D),
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 8.dp
                ) {
                    Text(
                        text = amphibian.type.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF081C15)
                    )
                }

                Text(
                    text = amphibian.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = amphibian.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB7E4C7),
                    maxLines = if (expanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 22.sp
                )
                
                TextButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = if (expanded) "Leer menos" else "Leer más",
                        color = Color(0xFF74C69D),
                        fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                
                Text(
                    text = "Explorador: Sheila Diaz Rojas",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF52B788),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
