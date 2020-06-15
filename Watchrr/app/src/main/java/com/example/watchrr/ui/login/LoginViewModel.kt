package com.example.watchrr.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.liveData
import com.example.watchrr.data.LoginRepository
import com.example.watchrr.data.Result

import com.example.watchrr.R
import com.example.watchrr.data.user.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()

    var loginRepository = LoginRepository()

    private val firebaseAuthLiveData = FirebaseAuthLiveData()

    fun getFireBaseAuthLiveData(): FirebaseAuthLiveData {
        return firebaseAuthLiveData
    }

    fun login(username: String, password: String) {
        val result = loginRepository.login(username, password)
    }

    fun signUpUser(email: String, password: String) {
        var result = loginRepository.signUp(email, password)

        if (result.isSuccessful) {
            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = "test"))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

}