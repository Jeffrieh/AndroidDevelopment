package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.afollestad.vvalidator.form
import com.example.myapplication.R
import com.example.myapplication.database.Game
import com.example.myapplication.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(toolbar)

        var mainActivityViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        val gameForm = form {
            input(R.id.tiTitle) {
                isNotEmpty()
            }

            input(R.id.tiPlatform) {
                isNotEmpty()
            }

            input(R.id.tiDay) {
                isNotEmpty()
                isNumber()
            }

            input(R.id.tiMonth) {
                isNotEmpty()
                isNumber()
            }

            input(R.id.tiYear) {
                isNotEmpty()
                isNumber()
            }

            submitWith(R.id.add) {}
        }

        add.setOnClickListener { view ->
            if (gameForm.validate().success()) {
                var title = etTitle.editText?.text.toString()
                var platform = etPlatform.editText?.text.toString()
                var day = etDay.editText?.text.toString()
                var month = etMonth.editText?.text.toString()
                var year = etYear.editText?.text.toString()

                var game = Game(null, title!!, platform!!, day!!, month!!, year!!)
                mainActivityViewModel.insert(game)

                val intent = Intent(this@AddActivity.applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
//
//            var title = etTitle.editText?.text.toString()
//            var platform = etPlatform.editText?.text.toString()
//            var day = etDay.editText?.text.toString()
//            var month = etMonth.editText?.text.toString()
//            var year = etYear.editText?.text.toString()
//
//            var game = Game(null, title!!, platform!!, day!!, month!!, year!!)
//            mainActivityViewModel.insert(game)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }

    }
}

