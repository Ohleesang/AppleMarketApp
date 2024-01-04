package com.example.applemarketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    object mlist{
        val value = mutableListOf<Item>()
    }
    
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}