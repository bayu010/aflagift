package com.example.alfagift.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movies(
    @PrimaryKey
    @ColumnInfo
    @SerializedName("movie_id")
    val movieId: Int?,

    @ColumnInfo
    @SerializedName("title")
    val movieTitle: String?,

    @ColumnInfo
    @SerializedName("poster_path")
    val posterPath: String?,

    @ColumnInfo
    @SerializedName("vote_average")
    val voteRating: Double?,

    @ColumnInfo
    @SerializedName("status")
    val statusMovie: String?,
)