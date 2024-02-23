package com.example.groceryshopping.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.groceryshopping.ui.theme.GroceryShoppingTheme
import com.example.groceryshopping.ui.viewmodel.GroceryListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryShoppingTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val viewModel: GroceryListViewModel = viewModel()
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}






