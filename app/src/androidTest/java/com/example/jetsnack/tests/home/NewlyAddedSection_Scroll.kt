package com.example.jetsnack.tests.home

// Import necessary Compose test APIs and UI geometry utilities
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import com.example.jetsnack.utils.UiTestRule
import org.junit.Test
import androidx.compose.ui.test.*




/**
 * Test class to validate the scrolling behavior of the "Newly Added" section
 * in the Jetsnack UI
 */
class NewlyAddedSection_Scroll : UiTestRule() {

    /**
     * Test to ensure the "Newly Added" section is reachable via scroll,
     * and that horizontal swipe gestures work correctly on snack items.
     */
    @Test
    fun home_displaysNewlyAddedSection_onLaunch() {

        // Custom extension function (assumed) to scroll the view until the given text is visible.
        // This would typically use a scroll gesture to bring the desired node into view.
        composeRule.swipeUntilVisible("Newly Added")

        // Verifies that a node with the text "Newly Added" is present and displayed on screen.
        composeRule.onNodeWithText("Newly Added").assertIsDisplayed()

        // Performs a left swipe gesture on the second item labeled "Chips" in the list of matching nodes.
        // This is likely simulating a horizontal carousel UI behavior.
        composeRule.onAllNodesWithText("Chips")[1].performTouchInput { swipeLeft() }

        // Performs a right swipe gesture on the second item labeled "Popcorn".
        composeRule.onAllNodesWithText("Popcorn")[1].performTouchInput { swipeRight() }

    }
}