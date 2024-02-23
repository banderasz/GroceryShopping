package com.example.groceryshopping.data.model

import androidx.compose.runtime.mutableStateListOf
import java.util.UUID

data class GroceryList(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val products: MutableList<Product> = mutableStateListOf()
)