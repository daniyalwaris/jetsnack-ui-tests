package com.example.jetsnack.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import com.example.jetsnack.ui.MainActivity
import org.junit.Rule


/**
 * Abstract base class to provide common setup and helper utilities
 * for all UI tests in the Jetsnack app.
 */
abstract class UiTestRule {

    /**
     * ComposeTestRule that sets up the test environment for Jetpack Compose,
     * launching the MainActivity by default.
     */
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    /**
     * Scrolls up repeatedly until a UI element with the specified text is visible,
     * or until a maximum number of swipe attempts is reached.
     *
     * @param targetText The visible text of the UI element to search for.
     * @param maxSwipes The maximum number of vertical swipe attempts (default: 15).
     * @param swipeDistance The distance in pixels to swipe upward each time (default: 1000f).
     * @param swipeDuration Duration of each swipe gesture in milliseconds (default: 300ms).
     * @throws AssertionError if the element is not found after the maximum attempts.
     */

    fun ComposeTestRule.swipeUntilVisible(
        targetText: String,
        maxSwipes: Int = 15,
        swipeDistance: Float = 1000f,
        swipeDuration: Long = 300
    ) {
        repeat(maxSwipes) { attempt ->
            try {
                // Attempt to find the target node and assert it's visible
                onNodeWithText(targetText).assertIsDisplayed()
                return  // Exit early if found
            } catch (e: AssertionError) {
                // If not found, perform an upward swipe to scroll
                onRoot().performTouchInput {
                    val startY = center.y
                    val endY = center.y - swipeDistance
                    swipe(
                        start = center.copy(y = startY),
                        end = center.copy(y = endY),
                        durationMillis = swipeDuration
                    )
                }
            }
        }
        // If loop ends and element is still not found, throw a failure
        throw AssertionError("Element with text '$targetText' not found after $maxSwipes swipes.")
    }


    /**
     * Performs horizontal swipe gestures back and forth on a UI element with the specified text.
     * Simulates user interaction with horizontal carousels or scrollable content.
     *
     * @param nodeText The text of the node to perform the swipe gesture on.
     * @param swipeDistance Horizontal swipe distance in pixels (default: 800f).
     * @param swipeDuration Duration of each swipe in milliseconds (default: 300ms).
     */

    fun ComposeTestRule.swipeNodeHorizontallyBackAndForth(
        nodeText: String,
        swipeDistance: Float = 800f,
        swipeDuration: Long = 300
    ) {
        // Locate the node with the specified text using the unmerged semantics tree
        val node = onNodeWithText(nodeText, useUnmergedTree = true)

        // Swipe left 3 times
        repeat(3) {
            node.performTouchInput {

                val centerX = this.visibleSize.width / 2f
                val centerY = this.visibleSize.height / 2f
                swipe(
                    start = Offset(centerX + swipeDistance / 2, centerY),
                    end = Offset(centerX - swipeDistance / 2, centerY),
                    durationMillis = swipeDuration
                )
            }
            Thread.sleep(300)  // Brief pause to simulate user-like interaction
        }

        // Swipe right 3 times
        repeat(3) {
            node.performTouchInput {
                val centerX = this.visibleSize.width / 2f
                val centerY = this.visibleSize.height / 2f
                swipe(
                    start = Offset(centerX - swipeDistance / 2, centerY),
                    end = Offset(centerX + swipeDistance / 2, centerY),
                    durationMillis = swipeDuration
                )
            }
            Thread.sleep(300)
        }
    }
}
