package com.example.groceryshopping.ui

import AuthViewModel
import LoginScreen
import RegisterScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.groceryshopping.ui.screens.GroceryListDetailScreen
import com.example.groceryshopping.ui.screens.GroceryListOverviewScreen
import com.example.groceryshopping.ui.viewmodel.GroceryListViewModel
import java.util.UUID

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val groceryListViewModel: GroceryListViewModel = viewModel()

    val authState by authViewModel.authState.collectAsState()

    when (authState) {
        is AuthViewModel.AuthState.SUCCESS -> {
            NavHost(navController = navController, startDestination = "groceryListScreen") {
                composable("groceryListScreen") {
                    GroceryListOverviewScreen(groceryListViewModel) { listId ->
                        navController.navigate("groceryListDetail/$listId")
                    }
                }
                composable(
                    route = "groceryListDetail/{listId}",
                    arguments = listOf(navArgument("listId") { type = NavType.StringType })
                ) { backStackEntry ->
                    GroceryListDetailScreen(
                        navController = navController,
                        listId = UUID.fromString(backStackEntry.arguments?.getString("listId")),
                        viewModel = groceryListViewModel
                    )
                }
                composable("register") {
                    RegisterScreen(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
                composable("login") {
                    LoginScreen(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
            }
        }

        else -> {
            // User is not authenticated, show the login screen
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
                composable("register") {
                    RegisterScreen(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
                // Define other composable routes here
            }
        }
    }


}