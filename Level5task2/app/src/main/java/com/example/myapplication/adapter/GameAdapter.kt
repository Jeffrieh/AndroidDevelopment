package com.example.myapplication.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.Game
import kotlinx.android.synthetic.main.inner_item.view.*
import java.text.SimpleDateFormat
import java.util.Calendar

class GameAdapter(
    private var games: MutableList<Game>,
    context: Context
) : RecyclerView.Adapter<GameAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Game>() // Cached copy of words
    lateinit var gamesBackup: MutableList<Game>;

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(game: Game) {
            itemView.tvtitle.text = game.title
            itemView.tvPlatform.text = game.platform

            var date = ""

            try {
                val calendarDate = Calendar.getInstance()
                calendarDate.set(game.year.toInt(), game.month.toInt(), game.day.toInt())
                val format1 = SimpleDateFormat("dd MMMM yyyy");
                date = format1.format(calendarDate.time)

            } catch (e: Exception) {
                date = "?"
            }

            itemView.tvDate.text = date

        }
    }

    fun add(game: Game) {
        this.games.add(game);
        notifyDataSetChanged()
    }

    fun softDelete() {
        gamesBackup = games;
        games = mutableListOf<Game>();
        notifyDataSetChanged()
    }

    fun softDelete(game: Game) {
        games.remove(game)
        notifyDataSetChanged()
    }

    fun revert() {
        games = gamesBackup;
        notifyDataSetChanged()
    }

    fun getGameAt(position: Int): Game {
        return games[position]
    }

    fun setGames(games: MutableList<Game>) {
        this.games = games;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.inner_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(games[position])
    }

    override fun getItemCount() = games.size
}