package com.example.groceryshopping.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groceryshopping.data.model.GroceryList
import com.example.groceryshopping.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

open class GroceryListViewModel : ViewModel() {
    companion object {
        private val TAG = GroceryListViewModel::class.java.simpleName
    }

    private val db = FirebaseFirestore.getInstance()

    private val _groceryLists = MutableStateFlow<List<GroceryList>>(emptyList())
    val groceryLists: StateFlow<List<GroceryList>> = _groceryLists

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    fun fetchGroceryLists(userId: String) = viewModelScope.launch {
        val snapshot = db.collection("groceryLists")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        val lists = snapshot.documents.map { document ->
            document.toObject(GroceryList::class.java)?.copy(id = document.id)
                ?: GroceryList()
        }
        _groceryLists.value = lists
    }

    fun addGroceryList(newGroceryListName: String, userId: String) {
        addGroceryList(
            GroceryList(
                name = newGroceryListName,
                userId = userId,
            )
        )
    }

    fun addGroceryList(groceryList: GroceryList) {
        db.collection("groceryLists").add(groceryList)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun removeGroceryList(groceryListId: String) {
        db.collection("groceryLists").document(groceryListId)
            .delete()
            .addOnSuccessListener {
                Log.d(
                    TAG,
                    "Grocery list ${groceryListId} successfully deleted!"
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    fun fetchProductsForList(listId: String) {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("groceryLists")
                    .document(listId)
                    .collection("products")
                    .get()
                    .await()

                val productsList = snapshot.documents.map { document ->
                    document.toObject(Product::class.java)?.copy(id = document.id)
                        ?: Product()
                }
                _products.value = productsList
            } catch (e: Exception) {
                // Handle any errors, e.g., log them or update UI to show an error message
                Log.e("Firestore", "Error fetching products for list $listId", e)
            }
        }
    }

    fun addProductToGroceryList(groceryListId: String, product: Product) {
        val listRef = db.collection("groceryLists").document(groceryListId)

        listRef.collection("products").add(product)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Product added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding product", e)
            }
    }

    fun removeProductFromGroceryList(groceryListId: String, productId: String) {
        db.collection("groceryLists").document(groceryListId)
            .collection("products").document(productId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Product successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting product", e)
            }
    }
}