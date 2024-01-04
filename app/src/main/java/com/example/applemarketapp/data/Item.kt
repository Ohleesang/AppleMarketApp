package com.example.applemarketapp.data

data class Item(
    val imgResource: Int,
    val name: String,
    val detail: String,
    val seller: String,
    val price: String,
    val address: String,
) {
    val like = 0
    val chat = 0

}