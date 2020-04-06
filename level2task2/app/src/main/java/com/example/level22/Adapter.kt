package com.example.level22

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question_list.view.*
import kotlinx.android.synthetic.main.activity_main.view.*
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import com.google.android.material.snackbar.Snackbar;


public class Adapter(private val questions: MutableList<Question>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_list, parent, false)
        )
    }

    fun removeItem(position: Int) = questions.removeAt(position);

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question: Question) {
            itemView.quiz_title.text = question.question
            itemView.setOnClickListener { onClick(question)  }
        }

        fun onClick(question: Question){
            val snack = Snackbar.make(itemView, "This Question is : ${question.answer}", 2000)
            snack.show()
        }
    }

}