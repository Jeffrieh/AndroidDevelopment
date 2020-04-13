package com.example.level32

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sites(val name: String, val address: String) : Parcelable
