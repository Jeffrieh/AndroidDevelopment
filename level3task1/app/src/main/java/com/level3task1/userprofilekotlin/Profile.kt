package com.level3task1.userprofilekotlin

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    val firstName: String,
    val lastName: String,
    val description: String,
    val imageUri: Uri?
) : Parcelable