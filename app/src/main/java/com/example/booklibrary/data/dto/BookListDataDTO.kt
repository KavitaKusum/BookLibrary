package com.example.booklibrary.data.dto

import com.google.gson.annotations.SerializedName

data class BookListDataDTO(
    @SerializedName("page")
    val page: Int,

    @SerializedName("numFound")
    val numFound: Int,

    @SerializedName("reading_log_entries")
    val readingLogEntries: List<BookDTO>
)