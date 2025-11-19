package com.example.booklibrary.domain.repository

import com.example.booklibrary.data.dto.BookDTO
import com.example.booklibrary.data.dto.BookListDataDTO
import com.example.booklibrary.data.dto.WorkDTO
import com.example.booklibrary.data.network.BookApiService
import com.example.booklibrary.data.repository.BookRepositoryImpl
import com.example.booklibrary.domain.model.Book
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BookRepositoryTest {

    private lateinit var bookRepository: BookRepositoryImpl
    private lateinit var mockApiService: BookApiService

    @Before
    fun setUp() {
        mockApiService = mock()
        bookRepository = BookRepositoryImpl(mockApiService)
    }

    @Test
    fun `getBooks successfully maps ApiResponse to List of Book`() {
        val fakeApiResponse = BookListDataDTO(1, 50,
            readingLogEntries = listOf(
                BookDTO(
                    work = WorkDTO(
                        title = "Lord of the Rings",
                        key = "/works/OL45804W",
                        authorKeys = listOf("/authors/OL21497A"),
                        authorNames = listOf("J.R.R. Tolkien"),
                        firstPublishYear = 1965,
                        lendingEditionS = "OL973653M",
                        editionKey = listOf("OL26501861M"),
                        coverId = 12345,
                        coverEditionKey = "OL26501861M"
                    ),
                    loggedEdition = "OL26501861M",
                    loggedDate = "2024-01-01"
                ),
                BookDTO(
                    work = WorkDTO(
                        title = "Wings of Fire",
                        key = "/works/OL46309W",
                        authorKeys = listOf("/authors/OL22216A"),
                        authorNames = listOf("Dr. A.P.J. Abdul Kalam"),
                        firstPublishYear = 1951,
                        lendingEditionS = "OL24188593M",
                        editionKey = listOf("OL24188593M"),
                        coverId = 54321,
                        coverEditionKey = "OL24188593M"
                    ),
                    loggedEdition = "OL24188593M",
                    loggedDate = "2024-02-01"
                )
            )
        )
        val expectedBooks = listOf(
            Book(
                coverId = 12345,
                title = "Lord of the Rings",
                authorNames = listOf("J.R.R. Tolkien")
            ),
            Book(
                coverId = 54321,
                title = "Wings of Fire",
                authorNames = listOf("Dr. A.P.J. Abdul Kalam")
            )
        )
        whenever(mockApiService.getBooks()).thenReturn(Single.just(fakeApiResponse))
        val testObserver = bookRepository.getBooks().test()
        testObserver.assertNoErrors()
        testObserver.assertValue(expectedBooks)
        testObserver.assertComplete()
    }

    @Test
    fun `getBooks propagates error when api service fails`() {
        val fakeError = RuntimeException("Network failure")
        whenever(mockApiService.getBooks()).thenReturn(Single.error(fakeError))
        val testObserver = bookRepository.getBooks().test()
        testObserver.assertError(fakeError)
        testObserver.assertNotComplete()
    }
}