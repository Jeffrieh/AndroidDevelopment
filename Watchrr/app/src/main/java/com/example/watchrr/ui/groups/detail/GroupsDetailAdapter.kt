package com.example.watchrr.ui.groups.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.watchrr.R
import com.example.watchrr.data.user.Movie
import kotlinx.android.synthetic.main.matches_item.view.*
import kotlin.collections.ArrayList

class GroupsDetailAdapter(
    private val myDataset: List<Movie>,
    private val context: Context
//    val adapterOnClick: (Group) -> Unit
) :
    RecyclerView.Adapter<GroupsDetailAdapter.MyViewHolder>() {

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }

        return this
    }

    class MyViewHolder(val view: View, context: Context) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie, context: Context) = with(itemView) {
            view.tvMovieTitle.text = movie.title
            Glide.with(context).load(movie.image_url).placeholder(R.drawable.ic_heart).into(view.ivMovie)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.matches_item, parent, false);

        return MyViewHolder(
            view,
            context
        ).listen { pos, type ->
            val item = myDataset[pos]
            //TODO do other stuff here
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(myDataset[position], context);
//        holder.view.setOnClickListener(adapterOnClick.onClick())
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}
