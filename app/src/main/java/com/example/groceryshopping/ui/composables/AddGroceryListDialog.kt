package com.example.groceryshopping.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddGroceryListDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var newListName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Grocery List") },
        text = {
            TextField(
                value = newListName,
                onValueChange = { newListName = it },
                label = { Text("Grocery List Name") }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (newListName.isNotBlank()) {
                        onConfirm(newListName)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}