package com.example.level6task2.data

import com.example.level6task2.ServiceBuilder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


class MovieRepository {

    val api_key = "72b7ea0485ed639ecc86f8038cc9bada";

    suspend fun getMovies(year: Int): Call<Movies> {
        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val call = request.getMovies(api_key, year)
        return call
    }

}

interface TmdbEndpoints {
    @GET("/3/movie/popular")
    fun getMovies(
        @Query("api_key") key: String,
        @Query("primary_release_year") year: Int
    ): Call<Movies>
}