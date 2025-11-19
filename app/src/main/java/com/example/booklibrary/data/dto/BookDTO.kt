package com.example.booklibrary.data.dto

import com.google.gson.annotations.SerializedName

data class BookDTO(
    @SerializedName("work")
    val work: WorkDTO,

    @SerializedName("logged_edition")
    val loggedEdition: String,

    @SerializedName("logged_date")
    val loggedDate: String
)

data class WorkDTO(
    @SerializedName("title")
    val title: String,

    @SerializedName("key")
    val key: String,

    @SerializedName("author_keys")
    val authorKeys: List<String>,

    @SerializedName("author_names")
    val authorNames: List<String>,

    @SerializedName("first_publish_year")
    val firstPublishYear: Int,

    @SerializedName("lending_edition_s")
    val lendingEditionS: String?,

    @SerializedName("edition_key")
    val editionKey: List<String>,

    @SerializedName("cover_id")
    val coverId: Int,

    @SerializedName("cover_edition_key")
    val coverEditionKey: String?
)