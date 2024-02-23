package com.example.groceryshopping.ui

import androidx.compose.runtime.Composable
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
fun AppNavigation(viewModel: GroceryListViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "groceryListScreen") {
        composable("groceryListScreen") {
            GroceryListOverviewScreen(viewModel) { listId ->
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
                viewModel = viewModel
            )
        }
    }
}