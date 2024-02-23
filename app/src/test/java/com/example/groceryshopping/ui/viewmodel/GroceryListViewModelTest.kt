package com.example.groceryshopping

import com.example.groceryshopping.ui.viewmodel.GroceryListViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GroceryListViewModelTest {
    @Test
    fun addGroceryList_updatesList() {
        // Given
        val viewModel = GroceryListViewModel()

        // When
        viewModel.addGroceryList("Test List")

        // Then
        assertEquals(1, viewModel.groceryLists.size)
        assertEquals("Test List", viewModel.groceryLists[0].name)
    }
}