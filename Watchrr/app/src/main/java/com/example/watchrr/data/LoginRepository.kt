package com.example.watchrr.data

import android.util.Log
import android.widget.Toast
import com.example.watchrr.data.model.LoggedInUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository() {

    private lateinit var auth: FirebaseAuth

    var user: FirebaseUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
        auth = Firebase.auth
    }

    fun logout() {
        user = null
        auth.signOut()
    }

    fun signUp(email: String, password: String): Task<AuthResult> {
        val result = auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    setLoggedInUser(auth.currentUser!!)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("USER", "createUserWithEmail:failure", task.exception)
                }
            }

        return result

    }

    fun login(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password)
    }

    private fun setLoggedInUser(loggedInUser: FirebaseUser) {
        this.user = loggedInUser
    }
}