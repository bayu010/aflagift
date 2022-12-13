package com.example.alfagift.model

import com.google.gson.annotations.SerializedName

data class MovieVideosModel(
    @SerializedName("iso_639_1")
    val iso639: String?,

    @SerializedName("iso_3166_1")
    val iso3166: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("key")
    val videoKey: String?,

    @SerializedName("site")
    val site: String?,

    @SerializedName("size")
    val videoSize: Int?,

    @SerializedName("type")
    val videoType: String?,

    @SerializedName("official")
    val isOfficial: Boolean?,

    @SerializedName("published_at")
    val publisedAt: String?,

    @SerializedName("id")
    val videoId: String?
)