package com.example.watchrr.data.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.watchrr.data.group.Group
import com.example.watchrr.data.movie.MovieRepository
import com.example.watchrr.ui.login.FirebaseAuthLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class UserRepository() {
    val TAG = "FIREBASE_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var user: MutableLiveData<User> = MutableLiveData()
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    var currentFireBaseUser: FirebaseUser
    var movieRepository = MovieRepository()

    init {
        currentFireBaseUser = Firebase.auth.currentUser!!

        //check if the current firebase user is set in our database.
        GlobalScope.launch {
            suspend {
                createUserIfNotExist()
            }
        }

    }

    fun listenToUser() {
        var docRef = firestoreDB.collection("users").document(currentFireBaseUser.uid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    user.value = querySnapshot.toObject(User::class.java)
                }
            }
    }

     fun createUser(): Task<Void> {
        val user = hashMapOf(
            "name" to currentFireBaseUser.displayName,
            "email" to currentFireBaseUser.email,
            "uuid" to currentFireBaseUser.uid
        )

        return firestoreDB.collection("users").document(currentFireBaseUser.uid).set(user)
    }

    suspend fun createUserIfNotExist() {
        println("were checking if user already exists..")
        var r = firestoreDB.collection("users").document(currentFireBaseUser.uid).get().await();
        if (!r.exists()) {
            createUser().addOnSuccessListener {
                listenToUser()
            }
        }else{
            listenToUser()
        }
    }

    suspend fun getUser(uuid: String): User {
        var t = firestoreDB.collection("users").document(uuid).get().await()
        return t.toObject(User::class.java)!!
    }

    suspend fun getUserByEmail(email: String): User? {
        var user: User? = null

        try {
            var t = firestoreDB.collection("users").whereEqualTo("email", email).get().await()
            user = t.first().toObject(User::class.java)
        } catch (e: Exception) {
            user = null
        }

        return user;
    }

    fun saveUser(user: User) {
        val t = firestoreDB.collection("users").document(user.uuid).set(user)
    }

    suspend fun getLikes(user: User): ArrayList<Movie> {
        val movies = arrayListOf<Movie>()
        val t = firestoreDB.collection("users").document("${user.uuid}").get().await()
        val mid = t.data?.get("movies") as ArrayList<String>?

        mid?.forEach {
            movieRepository.getMovie(it)?.let { it1 -> movies.add(it1) }
        }

        return movies
    }


    fun addGroupToUser(group: Group, user: User) {
        firestoreDB.collection("users").document(user.uuid).collection("groups").add(group)
    }

    fun resetLikes() {
        firestoreDB.collection("users").document(currentFireBaseUser.uid)
            .update("movies", null)
    }


}
