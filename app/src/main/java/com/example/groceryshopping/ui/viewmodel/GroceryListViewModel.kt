package com.example.groceryshopping.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.groceryshopping.data.model.GroceryList
import com.example.groceryshopping.data.model.Product
import java.util.UUID

class GroceryListViewModel : ViewModel() {
    private val _groceryLists = mutableStateListOf<GroceryList>()
    val groceryLists: List<GroceryList> = _groceryLists

    fun addGroceryList(listName: String) {
        _groceryLists.add(GroceryList(name = listName))
    }

    fun removeGroceryList(list: GroceryList) {
        _groceryLists.remove(list)
    }

    fun addProductToList(listId: UUID, product: Product) {
        _groceryLists.find { it.id == listId }?.products?.add(product)
    }

    fun removeProductFromList(listId: UUID, product: Product) {
        _groceryLists.find { it.id == listId }?.products?.remove(product)
    }
}