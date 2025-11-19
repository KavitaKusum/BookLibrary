package com.example.booklibrary.data.repository

import com.example.booklibrary.data.network.BookApiService
import com.example.booklibrary.domain.model.Book
import com.example.booklibrary.domain.repository.BookRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val apiService: BookApiService
) : BookRepository {

    override fun getBooks(): Single<List<Book>> {
        return apiService.getBooks().map { apiResponse ->
            apiResponse.readingLogEntries.map { entry ->
                Book(
                    coverId = entry.work.coverId,
                    title = entry.work.title,
                    authorNames = entry.work.authorNames
                )
            }
        }
    }
}