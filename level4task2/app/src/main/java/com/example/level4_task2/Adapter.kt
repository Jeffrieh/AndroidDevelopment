package com.example.level4_task2

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.history_list.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate

public class Adapter(private val item: List<HistoryItem>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    var resources = arrayOf<Int>(R.drawable.rock,R.drawable.paper,R.drawable.scissors)

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false)
        )
    }
    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: HistoryItem) {
            itemView.match_result_history.text = item.result
            itemView.match_time.text = item.date.toString()
            itemView.imageView.setImageDrawable(ContextCompat.getDrawable(
                itemView.context,
                resources[item.cpuMove-1]
            ))
            itemView.imageView2.setImageDrawable(ContextCompat.getDrawable(
                itemView.context,
                resources[item.playerMove-1]
            ))
        }

    }

}