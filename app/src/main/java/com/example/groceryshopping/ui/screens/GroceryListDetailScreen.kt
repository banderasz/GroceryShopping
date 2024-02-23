package com.example.groceryshopping.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.groceryshopping.data.model.Product
import com.example.groceryshopping.ui.composables.AddProductDialog
import com.example.groceryshopping.ui.composables.ProductItem
import com.example.groceryshopping.ui.viewmodel.GroceryListViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryListDetailScreen(
    navController: NavController,
    listId: UUID,
    viewModel: GroceryListViewModel
) {
    val list = viewModel.groceryLists.find { it.id == listId }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddProductDialog(
            onDismiss = { showDialog = false },
            onConfirm = { productName, productAmount ->
                viewModel.addProductToList(listId, Product(productName, productAmount))
                showDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Product")
            }
        },

        ) { innerPadding ->
        list?.let {
            LazyColumn(contentPadding = innerPadding) {
                items(it.products) { product ->
                    ProductItem(
                        product = product,
                        onRemove = { viewModel.removeProductFromList(listId, product) }
                    )
                }
            }
        }
    }
}