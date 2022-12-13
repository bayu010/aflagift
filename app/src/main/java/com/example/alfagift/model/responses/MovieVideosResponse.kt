package com.example.alfagift.model.responses

import com.example.alfagift.model.MovieVideosModel
import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(
    @SerializedName("id")
    val resultId: Int?,

    @SerializedName("results")
    val results: ArrayList<MovieVideosModel>?
)