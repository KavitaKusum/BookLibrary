package com.example.booklibrary.domain.repository

import com.example.booklibrary.domain.model.Book
import io.reactivex.rxjava3.core.Single

interface BookRepository {
    fun getBooks(): Single<List<Book>>
}
