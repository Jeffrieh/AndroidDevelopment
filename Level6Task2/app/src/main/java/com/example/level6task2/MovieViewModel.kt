package com.example.level6task2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.level6task2.data.MovieRepository
import com.example.level6task2.data.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    val movieRepository = MovieRepository()

    var movies: MutableLiveData<Response<Movies>>

    init {
        movies = MutableLiveData();
    }

    var selectedMovie : MutableLiveData<com.example.level6task2.data.Movie> = MutableLiveData()

    suspend fun getMovies(year : Int) {
        movieRepository.getMovies(year).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful) {
                    movies.postValue(response);
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
            }
        })

    }

}