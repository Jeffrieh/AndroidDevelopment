package com.example.watchrr.ui.groups

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.watchrr.R
import com.example.watchrr.data.group.Group
import kotlinx.android.synthetic.main.groups_item.view.*
import kotlin.collections.ArrayList

class GroupsAdapter(
    private val myDataset: ArrayList<Group>,
    private val context: Context,
    val adapterOnClick: (Group) -> Unit
) :
    RecyclerView.Adapter<GroupsAdapter.MyViewHolder>() {

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            adapterOnClick.invoke(myDataset[adapterPosition])
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(group: Group) = with(itemView) {
            view.mtrl_list_item_icon.setImageDrawable(context.getDrawable(R.drawable.ic_heart))
            view.mtrl_list_item_text.text = "You and ${group.users[1].email}"
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupsAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.groups_item, parent, false);

        return MyViewHolder(view).listen { pos, type ->
            val item = myDataset[pos]
            //TODO do other stuff here
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(myDataset[position]);
//        holder.view.setOnClickListener(adapterOnClick.onClick())
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}

interface AdapterOnClick {
    fun onClick(item: Any)
}