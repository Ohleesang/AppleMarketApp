package com.example.applemarketapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Item(
    val imgResource: Int,
    val name: String,
    val detail: String,
    val seller: String,
    val price: String,
    val address: String,
    var like: Int,
    var chat: Int,
) : Parcelable {

    var isCheckedLike = false
    constructor() : this(0, "", "", "", "", "", 0, 0)
}