package com.example.applemarketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.databinding.ActivityDetailBinding
import com.example.applemarketapp.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setImageElevation()
        setDataView()


    }

    //Image 위치 변경
    private fun setImageElevation() {
        binding.backBtnIv.elevation = 1F
        binding.itemIv.elevation = 0F
    }

    //데이터를 받아서 화면을 재구성
    private fun setDataView() {
        var item =
            intent.getParcelableExtra<Item>("clickedItem")
        item?.let {
            binding.itemIv.setImageResource(it.imgResource)

            binding.itemNameTv.text = it.name
            binding.userAddressTv.text = it.address
            binding.detailTv.text = it.detail
            binding.userNameTv.text = it.seller
            binding.priceTv.text = it.price.toString()
        }
    }
}