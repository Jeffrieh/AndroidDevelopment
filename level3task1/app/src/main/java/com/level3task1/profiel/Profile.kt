package com.level3task1.profiel

import android.net.Uri
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Profile(
    val name: String,
    val surname: String,
    val description: String,
    val picture: Uri?
) : Parcelable