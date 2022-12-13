package com.example.alfagift.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alfagift.api.ApiService
import com.example.alfagift.model.MovieModel
import com.example.alfagift.model.util.Resource
import com.example.alfagift.utils.ConstValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieInfoViewModel : ViewModel(), LifecycleObserver {
    val state = MutableLiveData<Resource<Any>>()
    val liveData = MutableLiveData<MovieModel>()

    fun getInfoMovie(movieId: Int, language: String? = "en-US") {
        CoroutineScope(Dispatchers.IO).launch {
            state.postValue(Resource.loading())
            ApiService.apiInterface.getMovieInfo(
                movieId = movieId,
                apiKey = ConstValue.API_KEY,
                language = language
            ).enqueue(object :
                Callback<MovieModel> {
                override fun onResponse(
                    call: Call<MovieModel>,
                    response: Response<MovieModel>
                ) {
                    if (response.isSuccessful) {
                        state.postValue(Resource.success())
                        liveData.postValue(response.body())
                    } else {
                        state.postValue(Resource.error(errorMessage = response.code().toString()))
                    }
                }

                override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                    state.postValue(Resource.error(errorMessage = t.message))
                }

            })
        }
    }
}