package com.example.booklibrary.ui.viewmodels

import com.example.booklibrary.domain.model.Book
import com.example.booklibrary.domain.usecase.ListUseCase
import com.example.booklibrary.ui.UIState
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BookListViewModelTest {

    // This rule swaps the background executor used by Architecture Components with a synchronous one.
    @get:Rule
    val instantTaskExecutorRule = RxImmediateSchedulerRule()

    private lateinit var mockUseCase: ListUseCase
    private lateinit var viewModel: BookListViewModel
    // Sample data for tests
    private val fakeBook = Book(12345, "Fake Title", listOf("Fake Author"))


    @Before
    fun setUp() {
        mockUseCase = mock()
        whenever(mockUseCase()).thenReturn(Single.never())
        viewModel = BookListViewModel(mockUseCase)
    }

    @Test
    fun `init sets UI state to Loading initially`() {
        assertTrue(viewModel.uiState.value is UIState.Loading)
    }

    @Test
    fun `fetchBooks success updates UI state to Success`() {
        val fakeBooks = listOf(fakeBook)
        whenever(mockUseCase()).thenReturn(Single.just(fakeBooks))
        viewModel.fetchBooks()
        val finalState = viewModel.uiState.value
        assertTrue(finalState is UIState.Success)
        assertEquals(fakeBooks, (finalState as UIState.Success).data)
    }

    @Test
    fun `fetchBooks error updates UI state to Error`() {
        val errorMessage = "Network Error"
        val exception = RuntimeException(errorMessage)
        whenever(mockUseCase()).thenReturn(Single.error(exception))
        viewModel.fetchBooks()
        val finalState = viewModel.uiState.value
        assertTrue(finalState is UIState.Error)
        assertEquals(errorMessage, (finalState as UIState.Error).message)
    }

    @Test
    fun `onBookSelected updates selectedBook state`() {
        viewModel.onBookSelected(fakeBook)
        assertEquals(fakeBook, viewModel.selectedBook.value)
    }

    @Test
    fun `onDismissBottomSheet clears selectedBook state`() {
        viewModel.onBookSelected(fakeBook)
        assertEquals(fakeBook, viewModel.selectedBook.value)
        viewModel.onDismissBottomSheet()
        assertEquals(null, viewModel.selectedBook.value)
    }
}