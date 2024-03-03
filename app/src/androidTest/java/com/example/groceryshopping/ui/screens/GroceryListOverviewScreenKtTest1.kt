package com.example.groceryshopping.ui.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.groceryshopping.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GroceryListOverviewScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addProductToGroceryList_displaysInList() {
        composeTestRule.onNodeWithContentDescription("Add Grocery List").performClick()

        composeTestRule.onNodeWithText("Grocery List Name").performTextInput("IKEA")
        composeTestRule.onNodeWithText("Add").performClick()
        composeTestRule.onNodeWithText("IKEA").assertExists()
    }
}