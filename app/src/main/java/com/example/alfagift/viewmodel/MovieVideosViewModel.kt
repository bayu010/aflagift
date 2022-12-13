package com.example.alfagift.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alfagift.api.ApiService
import com.example.alfagift.model.MovieVideosModel
import com.example.alfagift.model.responses.MovieVideosResponse
import com.example.alfagift.model.util.Resource
import com.example.alfagift.utils.ConstValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieVideosViewModel : ViewModel(), LifecycleObserver {
    val state = MutableLiveData<Resource<Any>>()
    val dataList = MutableLiveData<MovieVideosResponse>()

    fun getMovieVideos(movieId: Int, language: String? = "en-US") {
        CoroutineScope(Dispatchers.IO).launch {
            state.postValue(Resource.loading())
            ApiService.apiInterface.getMovieVideos(
                movieId = movieId,
                apiKey = ConstValue.API_KEY,
                language = language
            ).enqueue(object : Callback<MovieVideosResponse> {
                override fun onResponse(
                    call: Call<MovieVideosResponse>,
                    response: Response<MovieVideosResponse>
                ) {
                    if (response.isSuccessful) {
                        state.postValue(Resource.success())
                        dataList.postValue(response.body())
                    } else {
                        state.postValue(Resource.error(errorMessage = response.code().toString()))
                    }
                }

                override fun onFailure(call: Call<MovieVideosResponse>, t: Throwable) {
                    state.postValue(Resource.error(errorMessage = t.message))
                }

            })
        }
    }
}