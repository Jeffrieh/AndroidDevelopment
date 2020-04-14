package com.example.level4_task2

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.coroutines.*
import java.sql.Timestamp
import java.time.Instant
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    var resources = arrayOf<Int>(R.drawable.rock, R.drawable.paper, R.drawable.scissors)
    lateinit var historyRepo: HistoryRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rock.setOnClickListener({ playRound(1) })
        paper.setOnClickListener({ playRound(2) })
        scissor.setOnClickListener({ playRound(3) })
//        open_history.setOnClickListener({ openHistory() })
        setSupportActionBar(toolbar)

        historyRepo = HistoryRepo(this)

    }

    fun goto_history_activity(){
        val intent = Intent(this, History::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_show_history -> {
                goto_history_activity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun playRound(PlayerMove: Int) {
        val cpuMove = (1..3).random()

        move_cpu.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                resources[cpuMove - 1]
            )
        )

        move_you.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                resources[PlayerMove - 1]
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

        addMatch(HistoryItem(null, m_result,Timestamp(System.currentTimeMillis()), cpuMove, PlayerMove));
    }

    private fun addMatch(item: HistoryItem) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                historyRepo.insert(item)
            }
        }
    }
}
