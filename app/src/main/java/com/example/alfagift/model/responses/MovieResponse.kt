package com.example.alfagift.model.responses

import com.example.alfagift.model.MovieModel
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val resultMovies: ArrayList<MovieModel>?
)