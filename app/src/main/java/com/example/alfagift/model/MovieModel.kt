package com.example.alfagift.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("budget")
    val budgetMovie: Int?,

    @SerializedName("genres")
    val genres: ArrayList<GenreModel>?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>?,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("id")
    val movieId: Int?,

    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("revenue")
    val revenue: Int?,

    @SerializedName("runtime")
    val runTime: Int?,

    @SerializedName("status")
    val movieStatus: String?,

    @SerializedName("tagline")
    val tagline: String?,

    @SerializedName("title")
    val movieTitle: String?,

    @SerializedName("video")
    val video: Boolean?,

    @SerializedName("vote_average")
    val voteAverage: Double?,

    @SerializedName("vote_count")
    val voteCount: Int?,
)