package com.example.level32

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_portal.*
import android.view.MenuItem
import android.webkit.URLUtil
import android.widget.Toast


class AddPortal : AppCompatActivity() {

    private var portals = ArrayList<Sites>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_portal)
        initViews()

        setSupportActionBar(toolbar)

        // Now get the support action bar
        val actionBar = supportActionBar

        // Set toolbar title/app title
        actionBar!!.title = "Add Portals"

        // Display the app icon in action bar/toolbar
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === android.R.id.home) {
            val returnIntent = Intent(this, MainActivity::class.java)

            returnIntent.putParcelableArrayListExtra("portals", this.portals)

            startActivityForResult(returnIntent, 100)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        add_portal.setOnClickListener({ addPortal() })

        //get current portals from intent.
        val bundle = getIntent().extras
        portals = bundle?.getParcelableArrayList<Sites>("portals")!!
    }

    private fun addPortal() {
        var address = edit_address.text.toString()

        if (!URLUtil.isValidUrl(address)) {
            Toast.makeText(
                applicationContext,
                "Not a valid URL (must start with https:// or http://",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        var name = edit_name.text.toString()

        portals.add(Sites(name, address))
        Toast.makeText(applicationContext, "Added Portal!", Toast.LENGTH_SHORT).show()
    }
}
