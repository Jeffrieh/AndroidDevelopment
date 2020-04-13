package com.example.level32

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.portal_list.view.*
import java.lang.Exception


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
            itemView.button.text =
                sites.name + "\n" + sites.address.removePrefix("https://").removePrefix("http://")
            itemView.button.setOnClickListener { onClick(sites.address) }
        }

        fun onClick(address: String) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()

            try {
                customTabsIntent.launchUrl(itemView.context, Uri.parse(address));
            } catch (e: Exception) {
                Toast.makeText(itemView.context, "We could not launch this portal.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}