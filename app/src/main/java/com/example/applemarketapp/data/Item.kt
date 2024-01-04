package com.example.applemarketapp.data

data class Item(
    val imgResource: Int,
    val name: String,
    val detail: String,
    val seller: String,
    val price: Int,
    val address: String,
    val like: Int,
    val chat: Int
)