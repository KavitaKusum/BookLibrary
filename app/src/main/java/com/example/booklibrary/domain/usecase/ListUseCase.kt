package com.example.booklibrary.domain.usecase

import com.example.booklibrary.domain.model.Book
import com.example.booklibrary.domain.repository.BookRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

interface ListUseCase {
    operator fun invoke(): Single<List<Book>>
}

@Singleton
class ListUseCaseImpl @Inject constructor(
    private val repository: BookRepository
) : ListUseCase {
    override operator fun invoke(): Single<List<Book>> {
        return repository.getBooks()
    }
}