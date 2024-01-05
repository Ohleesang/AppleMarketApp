package com.example.applemarketapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Item (
    val imgResource: Int,
    val name: String,
    val detail: String,
    val seller: String,
    val price: String,
    val address: String,
    val like: Int,
    val chat: Int
):Parcelable
