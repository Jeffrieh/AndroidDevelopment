package com.example.level32

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.portal_list.view.*


public class Adapter(private val questions: MutableList<Sites>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.portal_list, parent, false)
        )
    }

    fun removeItem(position: Int) = questions.removeAt(position);
    fun addItem(site: Sites) = questions.add(site);

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sites: Sites) {
            itemView.button.text = sites.name + "\n" + sites.address
            itemView.button.setOnClickListener { onClick(sites.address)  }
        }

        fun onClick(address: String){
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(itemView.context, Uri.parse(address));
        }
    }

}