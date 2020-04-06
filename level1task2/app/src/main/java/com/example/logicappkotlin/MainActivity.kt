package com.example.logicappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private var correctAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener { getCorrectAnswers() }
    }

    private fun getCorrectAnswers(){
        this.correctAnswers = 0;

        if(input1.text.toString() == "T") correctAnswers++
        if(input2.text.toString() == "F") correctAnswers++
        if(input3.text.toString() == "F") correctAnswers++
        if(input4.text.toString() == "F") correctAnswers++

        val text = "Correct Answers is : ${correctAnswers}"
        val duration = Toast.LENGTH_SHORT

        var toast = Toast.makeText(applicationContext, text, duration);
        toast.show()
    }
}
