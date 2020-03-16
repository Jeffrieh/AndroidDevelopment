package com.example.level4_task2

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var resources = arrayOf<Int>(R.drawable.rock,R.drawable.paper,R.drawable.scissors)
    lateinit var db : AppDatabase;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rock.setOnClickListener({ playRound(1) })
        paper.setOnClickListener({ playRound(2) })
        scissor.setOnClickListener({ playRound(3) })

        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "HistoryItem.db"
        ).allowMainThreadQueries().build()

        open_history.setOnClickListener({openHistory()})
    }

    fun openHistory(){
        val intent = Intent(this, History::class.java)
//            intent.putParcelableArrayListExtra("names", portals as java.util.ArrayList<out Parcelable>)
        startActivity(intent)
    }

    fun playRound(PlayerMove: Int) {
        val cpuMove = (1..3).random()

        move_cpu.setImageDrawable(
                ContextCompat.getDrawable(
                        applicationContext,
                        resources[cpuMove-1]
                )
        )

        move_you.setImageDrawable(
                ContextCompat.getDrawable(
                        applicationContext,
                        resources[PlayerMove-1]
                )
        )

        var m_result = "";

        if (PlayerMove == cpuMove) {
            //draw
            match_result.text = "You Drew!"
            m_result = "draw"
        } else if ((PlayerMove == 1 && cpuMove == 3) || (PlayerMove == 2 && cpuMove == 1) || (PlayerMove == 3 && cpuMove == 2)) {
            //we win
            match_result.text = "You Win!"
            m_result = "won"

        } else {
            //we lose
            match_result.text = "You Lose!"
            m_result = "lose"

        }

        db.HistoryDao().insert(HistoryItem(null,m_result,cpuMove,PlayerMove))

    }
}
