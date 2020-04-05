package com.example.higher_lowerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentThrow: Int = 1
    private var lastThrow: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    /**
     * Set the initial (UI) state of the game.
     */
    private fun initViews() {
        btnHigher.setOnClickListener { diceHandler(0) }
        btnLower.setOnClickListener { diceHandler(1) }
        btnEqual.setOnClickListener { diceHandler(2) }
        updateUI()
    }

    private fun updateUI() {
        tvLastThrow.text = getString(R.string.last_throw, lastThrow)

        when (currentThrow) {
            1 -> ivDice.setImageResource(R.drawable.dice1)
            2 -> ivDice.setImageResource(R.drawable.dice2)
            3 -> ivDice.setImageResource(R.drawable.dice3)
            4 -> ivDice.setImageResource(R.drawable.dice4)
            5 -> ivDice.setImageResource(R.drawable.dice5)
            6 -> ivDice.setImageResource(R.drawable.dice6)
        }
    }

    private fun diceHandler(x: Int) {
        rollDice()
        when (x) {
            0 -> if (currentThrow > lastThrow) correct() else incorrect()
            1 -> if (currentThrow < lastThrow) correct() else incorrect()
            2 -> if (currentThrow == lastThrow) correct() else incorrect()
        }
    }

    private fun rollDice() {
        lastThrow = currentThrow
        currentThrow = (1..6).random()
        updateUI()
    }


    private fun correct() {
        Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
    }

    private fun incorrect() {
        Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
    }
}



