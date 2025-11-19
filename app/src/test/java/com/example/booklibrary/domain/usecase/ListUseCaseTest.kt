package com.example.booklibrary.domain.usecase

import com.example.booklibrary.domain.model.Book
import com.example.booklibrary.domain.repository.BookRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ListUseCaseTest {

    private lateinit var mockRepository: BookRepository
    private lateinit var listUseCase: ListUseCase

    @Before
    fun setUp() {
        mockRepository = mock()
        listUseCase = ListUseCaseImpl(mockRepository)
    }

    @Test
    fun `invoke calls getBooks on repository`() {
        val fakeBooks = listOf<Book>()
        whenever(mockRepository.getBooks()).thenReturn(Single.just(fakeBooks))
        val testObserver = listUseCase().test()
        verify(mockRepository).getBooks()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    @Test
    fun `invoke propagates error from repository`() {
        val expectedError = RuntimeException("Failed to fetch from repository")
        whenever(mockRepository.getBooks()).thenReturn(Single.error(expectedError))
        val testObserver = listUseCase().test()
        verify(mockRepository).getBooks()
        testObserver.assertError(expectedError)
        testObserver.assertNotComplete()
    }
}