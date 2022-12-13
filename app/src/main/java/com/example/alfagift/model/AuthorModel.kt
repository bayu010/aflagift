package com.example.alfagift.model

import com.google.gson.annotations.SerializedName

data class AuthorModel(

    @SerializedName("name")
    val name: String?,

    @SerializedName("username")
    val username: String?,

    @SerializedName("avatar_path")
    val avatarPath: String?,

    @SerializedName("rating")
    val rating: Double?,

)
