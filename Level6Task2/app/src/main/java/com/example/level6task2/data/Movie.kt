package com.example.level6task2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Movies(
    val results: ArrayList<Movie>
)

@Parcelize
data class Movie(
    val id: Int,    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable