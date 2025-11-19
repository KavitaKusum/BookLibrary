package com.example.booklibrary.ui.screens


import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.booklibrary.domain.model.Book
import com.example.booklibrary.ui.UIState
import com.example.booklibrary.ui.viewmodels.BookListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class BookListScreenTest {

    // createComposeRule provides a test environment for Jetpack Compose UI.
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: BookListViewModel
    private lateinit var uiStateFlow: MutableStateFlow<UIState<List<Book>>>
    private lateinit var selectedBookFlow: MutableStateFlow<Book?>

    // Sample data to be used in the tests.
    private val fakeBooks = listOf(
        Book(coverId = 1, title = "The Hobbit", authorNames = listOf("J.R.R. Tolkien")),
        Book(coverId = 2, title = "Wings of Fire", authorNames = listOf("Dr. A.P.J. Abdul Kalam"))
    )
    private val singleFakeBook = fakeBooks.first()

    @Before
    fun setUp() {
        uiStateFlow = MutableStateFlow(UIState.Loading)
        selectedBookFlow = MutableStateFlow(null)
        mockViewModel = mock()
        whenever(mockViewModel.uiState).thenReturn(uiStateFlow)
        whenever(mockViewModel.selectedBook).thenReturn(selectedBookFlow)
    }

    @Test
    fun loadingState_showsCircularProgressIndicator() {
        composeTestRule.setContent {
            BookListScreen(viewModel = mockViewModel)
        }
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun successState_showsListOfBooks() {
        uiStateFlow.value = UIState.Success(fakeBooks)
        composeTestRule.setContent {
            BookListScreen(viewModel = mockViewModel)
        }
        composeTestRule.onNodeWithText("The Hobbit").assertIsDisplayed()
        composeTestRule.onNodeWithText("by J.R.R. Tolkien").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wings of Fire").assertIsDisplayed()
        composeTestRule.onNodeWithText("by Dr. A.P.J. Abdul Kalam").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessageAndRetryButton() {
        val errorMessage = "Could not fetch books"
        uiStateFlow.value = UIState.Error(errorMessage)
        composeTestRule.setContent {
            BookListScreen(viewModel = mockViewModel)
        }
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun clickingRetryButton_callsFetchBooks() {
        uiStateFlow.value = UIState.Error("An error occurred")
        composeTestRule.setContent {
            BookListScreen(viewModel = mockViewModel)
        }
        composeTestRule.onNodeWithText("Retry").performClick()
        verify(mockViewModel).fetchBooks()
    }

    @Test
    fun clickingBookItem_callsOnBookSelected() {
        uiStateFlow.value = UIState.Success(listOf(singleFakeBook))
        composeTestRule.setContent {
            BookListScreen(viewModel = mockViewModel)
        }
        composeTestRule.onNodeWithText(singleFakeBook.title).performClick()
        verify(mockViewModel).onBookSelected(singleFakeBook)
    }

    @Test
    fun whenBookIsSelected_bottomSheetIsDisplayed() {
        uiStateFlow.value = UIState.Success(listOf(singleFakeBook))
        selectedBookFlow.value = singleFakeBook
        composeTestRule.setContent {
            BookListScreen(viewModel = mockViewModel)
        }
        composeTestRule.onAllNodesWithText(singleFakeBook.title).assertCountEquals(2)
        composeTestRule.onAllNodesWithText(singleFakeBook.title)[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(singleFakeBook.title)[1].assertIsDisplayed()
    }
}

