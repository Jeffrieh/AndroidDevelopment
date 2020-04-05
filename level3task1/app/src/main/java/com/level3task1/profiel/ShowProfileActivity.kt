package com.level3task1.profiel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_profile.*

class ShowProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Your Profile"

        val profile = intent.getParcelableExtra<Profile>(PROFILE_EXTRA)

        if (profile != null) {
            tvName.text = getString(R.string.name, profile.name, profile.surname)
            tvDescription.text = profile.description
            if (profile.picture != null) ivProfileImage.setImageURI(profile.picture)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val PROFILE_EXTRA = "PROFILE_EXTRA"
    }
}
