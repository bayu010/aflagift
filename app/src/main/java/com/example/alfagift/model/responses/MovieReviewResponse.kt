package com.example.alfagift.model.responses

import com.example.alfagift.model.MovieReviewModel
import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: ArrayList<MovieReviewModel>?
)