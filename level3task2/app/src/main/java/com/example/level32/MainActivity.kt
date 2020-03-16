package com.example.level32

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Parcelable


class MainActivity : AppCompatActivity() {

    private val portals = mutableListOf<Sites>()
    private val portalAdapter = Adapter(portals)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        initViews()

        val address = intent.getStringExtra("address")
        val name = intent.getStringExtra("name")

        if(address != null){
            portals.add(Sites(name,address))
        }

    }

    private fun initViews() {

        r_portals.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        r_portals.adapter = portalAdapter

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddPortal::class.java)
//            intent.putParcelableArrayListExtra("names", portals as java.util.ArrayList<out Parcelable>)
            startActivity(intent)
        }

        for (i in Sites.NAMES.indices) {
            portals.add(Sites(Sites.NAMES[i], Sites.ADDRESS[i]))
        }

        portalAdapter.notifyDataSetChanged()
    }
}
