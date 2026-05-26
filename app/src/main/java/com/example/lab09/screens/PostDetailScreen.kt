package com.example.lab09.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab09.viewmodel.PostViewModel

@Composable
fun PostDetailScreen(
    id: Int,
    viewModel: PostViewModel
) {
    val post by viewModel.selectedPost.collectAsState()
    LaunchedEffect(id) {
        viewModel.loadPostById(id)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFF0A0A0A))
    ) {
        // Hero Header con Gradiente Vibrante Morado-Rosa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF6200EE), Color(0xFFE91E63))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier.size(90.dp),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Info, null, modifier = Modifier.size(44.dp), tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "DETALLE PREMIUM",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.9f),
                    letterSpacing = 5.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        if (post != null) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                // Info Cards con Borde Luminoso
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    DetailCapsule(label = "POST ID", value = post!!.id.toString(), modifier = Modifier.weight(1f))
                    DetailCapsule(label = "USER ID", value = post!!.userId.toString(), modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = post!!.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    lineHeight = 38.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                
                HorizontalDivider(color = Color(0xFFE91E63).copy(alpha = 0.4f), thickness = 2.dp)
                
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = post!!.body,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray.copy(alpha = 0.85f),
                    lineHeight = 30.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(56.dp))

                // Botón Guardar con efecto de pulso
                val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                val scale by infiniteTransition.animateFloat(
                    initialValue = 1f, targetValue = 1.06f,
                    animationSpec = infiniteRepeatable(tween(800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
                    label = "scale"
                )

                Button(
                    onClick = { viewModel.saveFavorite(post!!) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .scale(scale)
                        .shadow(25.dp, RoundedCornerShape(22.dp), spotColor = Color(0xFFE91E63)),
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("MARCAR COMO FAVORITO", fontSize = 18.sp, fontWeight = FontWeight.Black)
                }

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "Sheila Diaz Rojas",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun DetailCapsule(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.border(BorderStroke(1.dp, Color(0xFF6200EE).copy(alpha = 0.4f)), RoundedCornerShape(20.dp)),
        color = Color(0xFF121212),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color(0xFFE91E63), fontWeight = FontWeight.Bold)
            Text(text = value, style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Black)
        }
    }
}
