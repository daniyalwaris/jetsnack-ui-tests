# 🧪 UI Test Architecture for Jetsnack (Jetpack Compose)
This project outlines the UI test strategy for **Jetsnack**,

---


## 🎯 Purpose of the Tests
These UI tests are created to:
- Validate key user journeys such as searching, browsing, and viewing product details.
- Validate the UI behaviour works correctly when app changes.
- Ensure that the newly items are loaded and responds correctly. 
- Ensure that the touch gesture (scrolling, swiping) work correctly.
- Ensure the high priority flows like navigation, app loading and product interaction works fine.

For example, tests like `SnackSearch` 
- ensure that users can search for items “Mango” and view correct product details  in user emulated way. 
- Also reducing the manual testing effort for repetitive task.
- Also help to identify issues in earlier part of Development lifecycle.
- It provide confidence in app stability. 



## 🧰 Test Suite Structure & Responsibilities

| **Test Suite**         | **Responsibility**                                                |
|------------------------|-------------------------------------------------------------------|
| User Prespentive suite | Tests that verify complete user flows from start to finish.       |
| Screen specific suite  | Tests focused on verifying individual screens function correctly. |
| Smoke Test Tags        | Tests focused on verifying critical elements or object.           |
| Regression Test Tags   | Tests focused on verifying all the flows.                         |






## 🧱 Sustainable & Scalable Test Structure
To make tests easy to write, maintain, and scale:

### ✅ Use Base Test Classes
The separate UI Behaviour from test class will promote the reuse and readability of the code.
Example: In `UiTestRule` centralizes rule setup and 
reusable gestures (`swipeUntilVisible`, `swipeNodeHorizontallyBackAndForth`).

### ✅ Name Tests Semantically
Use naming conventions like: 
fun search_returnsCorrectProduct_whenQueryIsValid()
fun home_displaysNewlyAddedSection_onLaunch()

## ✅ Adding a New Test
1. Add your test in the relevant package (e.g., `tests/search`). 
2. Creating screen or feature specific classes that will encapsulate the screen interactions.
3. Creating test data specific classes in order to maintain a dedicated data provider section.



### 🐛 Dealing with Flaky Tests
1. **Explicit Waiting Mechanisms**
Replace fixed delays with conditional waits:
Example
   // Wait for UI to settle after input.
   composeRule.waitUntil(10000) {
   val resultsExist = composeRule.onAllNodes(
   hasText("Mango") and hasText("$2.99", substring = true) and hasClickAction()
   ).fetchSemanticsNodes().isNotEmpty()
   println("Search results found: $resultsExist")
   resultsExist
   }

2. **Retry Mechanisms**
Implement retry capabilities for flaky operations:
Example:
  ` try {
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
   )`

   
 ### ⏳ Countermeasures for Test Structure
 1. Test the UI object via visual matching ( Snapshot testing) -Suggestion
 2. Tagging the critical one scenario in order to test the build during continuous integration.
 3. Prefer to use semantic nodes reliability to access the node function ( HasText(), HasSetTextAction())




## 📘 Conclusion

This testing strategy aims to keep UI tests:
- **Reliable** – with utilities that eliminate flakiness.
- **Maintainable** – by separating setup, logic, and page behavior.
- **Scalable** – via reusable structures and naming conventions.

