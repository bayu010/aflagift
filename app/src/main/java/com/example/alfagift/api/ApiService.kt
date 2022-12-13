package com.example.alfagift.api

import com.example.alfagift.model.MovieModel
import com.example.alfagift.model.responses.MovieResponse
import com.example.alfagift.model.responses.MovieVideosResponse
import com.example.alfagift.model.responses.MovieReviewResponse
import com.example.alfagift.utils.ConstValue
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object ApiService{

    val apiInterface: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(ConstValue.BASE_URL_V3)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClientHelper().initOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofit.create(ApiInterface::class.java)
    }

    interface ApiInterface {

        @GET("movie/top_rated")
        fun getTopRatedMovie(
            @Query("api_key") apiKey: String,
            @Query("language") language: String?,
            @Query("page") page: Int?
        ): Call<MovieResponse>

        @GET("movie/now_playing")
        fun getNowPlayingMovie(
            @Query("api_key") apiKey: String,
            @Query("language") language: String?,
            @Query("page") page: Int?,
            @Query("region") region: String?
        ): Call<MovieResponse>

        @GET("movie/{movie_id}")
        fun getMovieInfo(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String?
        ): Call<MovieModel>

        @GET("movie/{movie_id}/videos")
        fun getMovieVideos(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String?
        ): Call<MovieVideosResponse>

        @GET("movie/{movie_id}/reviews")
        fun getListReviews(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String?,
            @Query("page") page: Int?
        ): Call<MovieReviewResponse>
    }
}