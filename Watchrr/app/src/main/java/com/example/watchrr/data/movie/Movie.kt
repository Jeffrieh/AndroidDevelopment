package com.example.watchrr.data.user

import com.google.firebase.database.PropertyName
import java.io.Serializable
import java.util.*

data class Movie(
    @PropertyName("title") val title: String,
    @PropertyName("description") val description: String,
    @PropertyName("image_url") val image_url: String,
    @PropertyName("release_date") val release_date: String
) : Serializable {
    constructor() : this("", "", "", "")

    var uuid = UUID.randomUUID().toString()

    override fun equals(other: Any?): Boolean {
        var s: Movie

        if (other !is Movie) {
            return false
        }

        s = other;

        return this.title == s.title
    }
}