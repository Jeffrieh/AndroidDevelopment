package com.example.watchrr.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.watchrr.R

class SignupActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        loginViewModel = ViewModelProviders.of(this)
            .get(LoginViewModel::class.java)

        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val create = findViewById<Button>(R.id.create)

        create.setOnClickListener {
            loginViewModel.signUpUser(email.text.toString(), password.text.toString())
        }

    }


}