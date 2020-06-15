package com.example.watchrr.data.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.watchrr.data.group.Group
import com.example.watchrr.data.user.Movie
import com.example.watchrr.data.user.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class MovieRepository {
    val TAG = "FIREBASE_REPOSITORY_MOVIE"
    var firestoreDB = FirebaseFirestore.getInstance()
    var currentFireBaseUser: FirebaseUser
    var movies = MutableLiveData<List<Movie>>()

    init {
        currentFireBaseUser = Firebase.auth.currentUser!!
        getMovies()
    }

    fun likeMovie(movie: Movie) {
        println("liking movie : ${movie.title}")
        firestoreDB.collection("users").document(currentFireBaseUser.uid)
            .update("movies", FieldValue.arrayUnion(movie.title))
    }

    fun addMovie(movie: Movie) {
        firestoreDB.collection("movies").document(movie.title).set(movie)
    }

    suspend fun getMovieByTitle(title: String): Movie {
        val t = firestoreDB.collection("movies").whereEqualTo("title", title).get().await()
        return t.first().toObject(Movie::class.java)!!
    }

    suspend fun getMovie(mid: String): Movie? {
        val t = firestoreDB.collection("movies").document(mid).get().await()
        return t.toObject(Movie::class.java)
    }

    fun retrieveMovies(): MutableLiveData<List<Movie>> {
        return this.movies
    }

    fun getMovies() {
        val movieRef = firestoreDB.collection("movies")
        movieRef.addSnapshotListener { documents, _ ->
            if (documents != null) {
                movies.value = documents.toObjects(Movie::class.java)
            }
        }
    }

}
