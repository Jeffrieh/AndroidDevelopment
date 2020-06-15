package com.example.level6task2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.level6task2.data.Movie

class SimpleItemRecyclerViewAdapter(
    private val context: Context,
    private val values: ArrayList<Movie>,
    private val onClickListener: View.OnClickListener
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Glide.with(context.applicationContext)
            .load("http://image.tmdb.org/t/p/w500${item.poster_path}").into(holder.image)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posView: TextView = view.findViewById(R.id.id_position)
        val image: ImageView = view.findViewById(R.id.ivPoster)
    }
}