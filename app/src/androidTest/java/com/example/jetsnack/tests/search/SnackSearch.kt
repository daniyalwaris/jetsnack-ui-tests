package com.example.jetsnack.tests.search
/*
 * Test Case: Search for “Mango” & Open Details
 *
 * Purpose:
 * Ensure the free-text search functionality works correctly and
 * that the product detail screen renders properly upon selection.
 *
 * Test Steps:
 * 1. Tap the Search tab.
 * 2. Enter “Mango” into the search input field and submit.
 * 3. Tap the Mango item in the search results.
 *
 * Assertions:
 * - The Mango card appears in the results list.
 * - The detail screen for Mango displays the title, price, description,
 *   and ingredients section.
 */

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.jetsnack.R
import com.example.jetsnack.utils.UiTestRule
import org.junit.Test

/**
 * Test class to validate search functionality and navigation
 * to product details in the Jetsnack app.
 */
class SnackSearch: UiTestRule() {

    /**
     * Test to perform a search for "Mango" and validate
     * the result and details screen rendering.
     */
    @Test
    fun search_returnsCorrectProduct_whenQueryIsValid() {
        println("---------------- can_searchForProduct")

        // Step 1: Click on the "SEARCH" tab to navigate to the search view.
        composeRule.onNodeWithText("SEARCH").performClick()

        // Add explicit wait to ensure the search screen is fully loaded
        composeRule.waitUntil(5000) {
            composeRule.onAllNodes(hasSetTextAction()).fetchSemanticsNodes().size > 0
        }

        // Step 2: Find the text input field and enter "Mango"
        composeRule.onNode(hasSetTextAction()).performTextInput("Mango")

        // Wait for UI to settle after input.
        composeRule.waitUntil(10000) {
            val resultsExist = composeRule.onAllNodes(
                hasText("Mango") and hasText("$2.99", substring = true) and hasClickAction()
            ).fetchSemanticsNodes().isNotEmpty()
            println("Search results found: $resultsExist")
            resultsExist
        }

        // Step 3: Click on the Mango item from search results.
        // This node must contain the text "Mango" and "$2.99" with a clickable action.
        composeRule.onNode(
            hasText("Mango") and
                    hasText("$2.99", substring = true) and
                    hasClickAction()
        ).performClick()

        // Add explicit wait for the detail screen to load
        composeRule.waitUntil(5000) {
            composeRule.onAllNodes(hasText("Mango")).fetchSemanticsNodes().size > 0 &&
                    composeRule.onAllNodes(hasText("$2.99")).fetchSemanticsNodes().size > 0
        }

        // Assertion 1: Check if the product title "Mango" is visible on the detail screen.
        composeRule.onNodeWithText("Mango").assertIsDisplayed()

        // Assertion 2: Check if the product price "$2.99" is displayed.
        composeRule.onNodeWithText("$2.99").assertIsDisplayed()

        // Assertion 3: Check for the product description.
        // This uses a string resource and partial match, in case it's truncated with "see more"
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.detail_placeholder),
            substring = true // Only need partial match as it might be truncated with "see more"
        ).assertIsDisplayed()

        // Assertion 4.1: Check if the ingredients section title is present.
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.ingredients)
        ).assertIsDisplayed()

        // Assertion 4.2: Check if the ingredients list is partially or fully displayed.
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.ingredients_list),
            substring = true
        ).assertIsDisplayed()
    }
}