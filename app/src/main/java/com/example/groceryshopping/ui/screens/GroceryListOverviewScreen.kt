package com.example.groceryshopping.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.groceryshopping.ui.composables.AddGroceryListDialog
import com.example.groceryshopping.ui.composables.GroceryListItem
import com.example.groceryshopping.ui.viewmodel.GroceryListViewModel
import java.util.UUID

@Composable
fun GroceryListOverviewScreen(viewModel: GroceryListViewModel, openList: (UUID) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddGroceryListDialog(onDismiss = { showDialog = false },
            onConfirm = { newListName ->
                viewModel.addGroceryList(newListName)
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
        val groceryLists by remember { mutableStateOf(viewModel.groceryLists) }

        LazyColumn(contentPadding = innerPadding) {
            items(groceryLists) { list ->
                GroceryListItem(
                    groceryList = list,
                    onRemove = { viewModel.removeGroceryList(list) },
                    onClick = { openList(list.id) }
                )
            }
        }
    }
}