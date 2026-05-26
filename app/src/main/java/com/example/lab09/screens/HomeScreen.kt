package com.example.lab09.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab09.viewmodel.PostViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: PostViewModel
) {
    val posts by viewModel.posts.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        // Decorative background elements for glassmorphism context
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-100).dp, y = (-100).dp)
                .background(Color(0xFF6200EE).copy(alpha = 0.15f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = 50.dp)
                .background(Color(0xFF03DAC6).copy(alpha = 0.15f), CircleShape)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp, start = 16.dp, end = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = "Premium Feed",
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Sheila Diaz Rojas",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFBB86FC),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            itemsIndexed(posts) { index, post ->
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { visible = true }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(600, delayMillis = index * 100)) + 
                            scaleIn(initialScale = 0.8f) +
                            slideInVertically(initialOffsetY = { 100 }),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PostGlassCard(
                        title = post.title,
                        id = post.id,
                        onClick = { navController.navigate("detail/${post.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun PostGlassCard(title: String, id: Int, onClick: () -> Unit) {
    val cardGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF6200EE).copy(alpha = 0.2f), 
            Color(0xFF03DAC6).copy(alpha = 0.2f)
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color(0xFF6200EE),
                spotColor = Color(0xFF03DAC6)
            )
            .clip(RoundedCornerShape(28.dp))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color.White.copy(alpha = 0.4f), Color.Transparent)
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier
                .background(cardGradient)
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Avatar con inicial y gradiente
            Surface(
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                color = Color.Transparent,
                border = androidx.compose.foundation.BorderStroke(
                    2.dp, 
                    Brush.linearGradient(listOf(Color(0xFFBB86FC), Color(0xFF03DAC6)))
                )
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = title.take(1).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "POST #$id",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF03DAC6),
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1
                )
                Text(
                    text = "Sheila Diaz Rojas",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
