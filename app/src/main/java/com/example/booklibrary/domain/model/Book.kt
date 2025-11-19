package com.example.booklibrary.domain.model

data class Book(
    val coverId: Int,
    val title: String,
    val authorNames: List<String>)