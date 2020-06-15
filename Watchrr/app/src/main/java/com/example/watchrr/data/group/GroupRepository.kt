package com.example.watchrr.data.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.watchrr.data.user.Movie
import com.example.watchrr.data.user.User
import com.example.watchrr.data.user.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class GroupRepository {
    val TAG = "FIREBASE_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var currentFireBaseUser: FirebaseUser
    val userRepository = UserRepository()
    var groups: MutableLiveData<List<Group>> = MutableLiveData()

    init {
        currentFireBaseUser = Firebase.auth.currentUser!!
        listenGroups()
    }

    fun getGroups(uid: String): CollectionReference {
        return firestoreDB.collection("users").document(currentFireBaseUser.uid)
            .collection("groups")
    }

    fun getGroup(gid: String): DocumentReference {
        return firestoreDB.collection("groups").document(gid)
    }

    fun createGroup(users: ArrayList<User>) {
        val newGroup = Group(users)

        if (users.size >= 2) {
            users.forEach {
                firestoreDB.collection("users").document(it.uuid).collection("groups")
                    .document(newGroup.uuid).set(newGroup)
            }
        } else {
            println("group must have atleast 2 people.")
        }
    }

    fun listenGroups() {
        val colRef =
            firestoreDB.collection("users").document(currentFireBaseUser.uid).collection("groups")

        colRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            snapshot?.documents?.forEach {
                groups.value = snapshot.toObjects(Group::class.java);
            }
        }

    }

    suspend fun getMatchesInGroup(group: Group): List<Movie> {
        var allLikedMovies = arrayListOf<Movie>()

        group.users.forEach {
            allLikedMovies.addAll(userRepository.getLikes(it));
        }

        allLikedMovies.forEach {
            if (Collections.frequency(allLikedMovies, it) < group.users.size) {
                allLikedMovies.remove(it)
            }
        }

        return allLikedMovies.distinct()
    }

}