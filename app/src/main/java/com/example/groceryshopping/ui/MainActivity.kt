package com.example.groceryshopping.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.groceryshopping.ui.theme.GroceryShoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryShoppingTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}






