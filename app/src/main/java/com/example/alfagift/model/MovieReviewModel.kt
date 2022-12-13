package com.example.alfagift.model

import com.google.gson.annotations.SerializedName

data class MovieReviewModel(
    @SerializedName("author")
    val author: String?,

    @SerializedName("author_details")
    val authorDetails: AuthorModel?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("id")
    val reviewId: String?,

    @SerializedName("updated_at")
    val updateAt: String?,

    @SerializedName("url")
    val url: String?
)