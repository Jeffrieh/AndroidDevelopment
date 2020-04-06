package com.example.level22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar;
import kotlinx.android.synthetic.main.question_list.view.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Question>()
    private val questionAdapter = Adapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setUpItemTouchHelper()
    }

    private fun setUpItemTouchHelper() {
        val simpleItemTouchCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    val position = viewHolder.adapterPosition
                    val arrPos = Question.QUESTIONS.indexOf(viewHolder.itemView.quiz_title.text)

                    if ((swipeDir == 4 && !Question.ANSWERS[arrPos]) || (swipeDir == 8 && Question.ANSWERS[arrPos])) {
                        questionAdapter.removeItem(position)
                    } else {
                        val snack = Snackbar.make(viewHolder.itemView, "You got it wrong", 2000)
                        snack.show()
                    }

                    questionAdapter.notifyDataSetChanged()
                }

            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(quiz_view)
    }

    private fun initViews() {
        quiz_view.layoutManager = LinearLayoutManager(applicationContext)
        quiz_view.adapter = questionAdapter

        // Populate the places list and notify the data set has changed.
        for (i in Question.QUESTIONS.indices) {
            questions.add(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))
        }

        questionAdapter.notifyDataSetChanged()
    }
}

