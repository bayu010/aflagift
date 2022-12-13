package com.example.alfagift.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alfagift.api.ApiService
import com.example.alfagift.model.responses.MovieReviewResponse
import com.example.alfagift.model.util.Resource
import com.example.alfagift.utils.ConstValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieReviewsViewModel : ViewModel(), LifecycleObserver {
    val state = MutableLiveData<Resource<Any>>()
    val dataList = MutableLiveData<MovieReviewResponse>()

    fun getMovieReviews(movieId: Int, language: String? = "en-US", page: Int? = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            state.postValue(Resource.loading())
            ApiService.apiInterface.getListReviews(
                movieId = movieId,
                apiKey = ConstValue.API_KEY,
                language = language,
                page = page
            ).enqueue(object : Callback<MovieReviewResponse> {
                override fun onResponse(
                    call: Call<MovieReviewResponse>,
                    response: Response<MovieReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        state.postValue(Resource.success())
                        dataList.postValue(response.body())
                    } else {
                        state.postValue(Resource.error(errorMessage = response.code().toString()))
                    }
                }

                override fun onFailure(call: Call<MovieReviewResponse>, t: Throwable) {
                    state.postValue(Resource.error(errorMessage = t.message))
                }
            })
        }
    }
}