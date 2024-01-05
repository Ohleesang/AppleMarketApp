package com.example.applemarketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applemarketapp.databinding.ActivityDetailBinding
import com.example.applemarketapp.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setImageElevation()

    }
    private fun setImageElevation(){
        binding.backBtnIv.elevation=1F
        binding.itemIv.elevation=0F
    }
}