package com.example.groceryshopping.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.groceryshopping.ui.composables.AddGroceryListDialog
import com.example.groceryshopping.ui.composables.GroceryListItem
import com.example.groceryshopping.ui.viewmodel.GroceryListViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun GroceryListOverviewScreen(viewModel: GroceryListViewModel, openList: (String) -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    val userId = user?.uid

    if (userId != null) {
        LaunchedEffect(userId) {
            viewModel.fetchGroceryLists(userId)
        }
    }


    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
        AddGroceryListDialog(onDismiss = { showDialog = false },
            onConfirm = { newGroceryListName ->
                viewModel.addGroceryList(newGroceryListName, userId.orEmpty())
                showDialog = false
            })
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Grocery List")
            }
        }
    ) { innerPadding ->
        val groceryLists by viewModel.groceryLists.collectAsState()
        LazyColumn(contentPadding = innerPadding) {
            items(groceryLists) { groceryList ->
                GroceryListItem(
                    groceryList = groceryList,
                    onRemove = { viewModel.removeGroceryList(groceryList.id) },
                    onClick = { openList(groceryList.id) }
                )
            }
        }
    }
}