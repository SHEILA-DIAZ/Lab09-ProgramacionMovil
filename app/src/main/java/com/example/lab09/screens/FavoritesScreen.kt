package com.example.lab09.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab09.viewmodel.PostViewModel
import kotlin.random.Random

@Composable
fun FavoritesScreen(viewModel: PostViewModel) {
    val favorites by viewModel.favorites.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadFavorites() }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF050505))) {
        StarryBackground()

        if (favorites.isEmpty()) {
            EmptyFavorites()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp, start = 16.dp, end = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mis Tesoros",
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Surface(
                            color = Color.Red,
                            shape = CircleShape,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${favorites.size}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
                items(favorites) { post ->
                    FavoriteNeonCard(post, onDelete = { viewModel.deleteFavorite(post.id) })
                }
            }
        }
    }
}

@Composable
fun FavoriteNeonCard(post: com.example.lab09.data.local.PostEntity, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(15.dp, RoundedCornerShape(24.dp), spotColor = Color.Red)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(listOf(Color.Red.copy(0.6f), Color.Transparent)),
                shape = RoundedCornerShape(24.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF121212))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PulsingHeartIcon()
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "FAVORITO #${post.id}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Red,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sheila Diaz Rojas",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Red.copy(alpha = 0.5f)
                )
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Red.copy(0.15f), CircleShape)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun PulsingHeartIcon() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.4f,
        animationSpec = infiniteRepeatable(tween(700, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "scale"
    )
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = null,
        tint = Color.Red,
        modifier = Modifier.size(20.dp).graphicsLayer(scaleX = scale, scaleY = scale)
    )
}

@Composable
fun StarryBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.1f, targetValue = 0.7f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Reverse),
        label = "alpha"
    )
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        val random = java.util.Random(7)
        repeat(80) {
            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = random.nextFloat() * 2.5f,
                center = Offset(random.nextFloat() * size.width, random.nextFloat() * size.height)
            )
        }
    }
}

@Composable
fun EmptyFavorites() {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.size(160.dp).blur(40.dp).background(Color.Red.copy(0.1f), CircleShape))
            Icon(Icons.Default.Favorite, null, modifier = Modifier.size(100.dp), tint = Color(0xFF222222))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Tu Espacio Premium",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Explora y guarda lo mejor para verlo aquí con un diseño excepcional.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}
