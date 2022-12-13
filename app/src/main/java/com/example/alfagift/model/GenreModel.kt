package com.example.alfagift.model

import com.google.gson.annotations.SerializedName

data class GenreModel(

    @SerializedName("id")
    val genreId: Int?,

    @SerializedName("name")
    val genreName: String?,

)
