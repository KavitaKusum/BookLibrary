package com.example.booklibrary.data.network

import com.example.booklibrary.data.dto.BookListDataDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface BookApiService {
    /* this app shows currently reading books api response-
    https://openlibrary.org/people/mekBot/books/currently-reading.json
    below apis can be used too:
    https://openlibrary.org/people/mekBot/books/want-to-read.json
    https://openlibrary.org/people/mekBot/books/already-read.json
    */
    @GET("currently-reading.json")
    fun getBooks(): Single<BookListDataDTO>
}