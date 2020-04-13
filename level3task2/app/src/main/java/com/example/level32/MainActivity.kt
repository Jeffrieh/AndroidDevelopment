package com.example.level32

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {

    private val portals = mutableListOf<Sites>()
    private val portalAdapter = Adapter(portals)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar!!.title = "My Portals"
        actionBar.setLogo(R.mipmap.ic_launcher)

        initViews()
    }

    private fun initViews() {

        r_portals.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        r_portals.adapter = portalAdapter

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddPortal::class.java)
            intent.putParcelableArrayListExtra(
                "portals", ArrayList(portals)
            )

            startActivity(intent)
        }

        val int = this.getIntent();
        if (int.hasExtra("portals")) {
            var bundle = int.extras
            var arr = bundle?.getParcelableArrayList<Sites>("portals")!!
            arr.forEach { portals.add(it) }
        }else{
            //fill the portals with some defaults.
            portals.add(Sites("DLO", "https://www.dlo.mijnhva.nl"))
            portals.add(Sites("SIS", "https://sis.hva.nl"))
        }

        portalAdapter.notifyDataSetChanged()
    }

}
