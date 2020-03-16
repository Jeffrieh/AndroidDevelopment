package com.example.level32

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_portal.*
import kotlinx.android.synthetic.main.activity_main.*


class AddPortal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_portal)
        initViews()
    }

    private fun initViews(){
        add_portal.setOnClickListener({addPortal()})
    }

    private fun addPortal(){
        var address = edit_address.text.toString()
        var name = edit_name.text.toString()

        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("name",name)
        intent.putExtra("address",address)

        startActivity(intent)
    }
}
