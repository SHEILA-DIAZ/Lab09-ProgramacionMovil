package com.example.lab09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab09.data.local.AppDatabase
import com.example.lab09.data.remote.ApiClient
import com.example.lab09.data.repository.AmphibianRepository
import com.example.lab09.data.repository.PostRepository
import com.example.lab09.data.repository.ProductRepository
import com.example.lab09.screens.*
import com.example.lab09.ui.theme.Lab09Theme
import com.example.lab09.viewmodel.AmphibianViewModel
import com.example.lab09.viewmodel.PostViewModel
import com.example.lab09.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val postRepository = PostRepository(ApiClient.postApiService, database.postDao())
        val amphibianRepository = AmphibianRepository(ApiClient.amphibianApiService)
        val productRepository = ProductRepository(ApiClient.productApiService)

        enableEdgeToEdge()
        setContent {
            Lab09Theme {
                val postVM: PostViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T = PostViewModel(postRepository) as T
                })
                val amphibianVM: AmphibianViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T = AmphibianViewModel(amphibianRepository) as T
                })
                val productVM: ProductViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T = ProductViewModel(productRepository) as T
                })

                MainApp(postVM, amphibianVM, productVM)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    postViewModel: PostViewModel,
    amphibianViewModel: AmphibianViewModel,
    productViewModel: ProductViewModel
) {
    val navController = rememberNavController()
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sheila Diaz Rojas - Lab 09", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // Tab: Inicio (Posts)
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                // Tab: Favoritos
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") },
                    selected = currentDestination?.hierarchy?.any { it.route == "favorites" } == true,
                    onClick = {
                        navController.navigate("favorites") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                // Tab: Anfibios
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Anfibios") },
                    label = { Text("Anfibios") },
                    selected = currentDestination?.hierarchy?.any { it.route == "amphibians" } == true,
                    onClick = {
                        navController.navigate("amphibians") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                // Tab: Tienda
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Tienda") },
                    label = { Text("Tienda") },
                    selected = currentDestination?.hierarchy?.any { it.route == "products" || it.route == "product_detail" } == true,
                    onClick = {
                        navController.navigate("products") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController, postViewModel) }
            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                PostDetailScreen(id, postViewModel)
            }
            composable("favorites") { FavoritesScreen(postViewModel) }
            composable("amphibians") { AmphibianHomeScreen(amphibianViewModel) }
            composable("products") { ProductHomeScreen(navController, productViewModel) }
            composable("product_detail") { ProductDetailScreen(navController, productViewModel) }
        }
    }
}
