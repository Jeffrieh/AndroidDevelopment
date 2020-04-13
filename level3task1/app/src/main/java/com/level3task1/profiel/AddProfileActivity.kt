package com.level3task1.profiel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_profile.*

class AddProfileActivity : AppCompatActivity() {

    private var picture: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        initViews()
    }

    private fun initViews() {
        btnConfirm.setOnClickListener { onClick() }
        btnGallery.setOnClickListener { pickImageFromGallery() }
    }

    private fun pickImageFromGallery() {
        // Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun onClick() {
        val profile = Profile(
            etFirstName.text.toString(),
            etLastName.text.toString(),
            etProfileDescription.text.toString(),
            picture
        )

        val profileActivityIntent = Intent(this, ShowProfileActivity::class.java)
        profileActivityIntent.putExtra(ShowProfileActivity.PROFILE_EXTRA, profile)
        startActivity(profileActivityIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    picture = data?.data
                    ivProfileImage.setImageURI(picture)
                }
            }
        }
    }

    companion object {
        const val IMAGE_PICK_CODE = 1000
    }
}
