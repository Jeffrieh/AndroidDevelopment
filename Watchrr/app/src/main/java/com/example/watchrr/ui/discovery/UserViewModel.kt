package com.example.watchrr.ui.discovery

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.watchrr.data.group.Group
import com.example.watchrr.data.group.GroupRepository
import com.example.watchrr.data.movie.MovieRepository
import com.example.watchrr.data.user.Movie
import com.example.watchrr.data.user.User
import com.example.watchrr.data.user.UserRepository
import com.example.watchrr.ui.login.FirebaseAuthLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class UserViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "USER_VIEW_MODEL"
    val firebaseAuthLiveData = FirebaseAuthLiveData()
}