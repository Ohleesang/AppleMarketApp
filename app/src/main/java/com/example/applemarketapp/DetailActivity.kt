package com.example.applemarketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
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


        backBtnOnClick()

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
        item?.run {
            binding.itemIv.setImageResource(imgResource)

            binding.itemNameTv.text = name
            binding.addressTv.text = address
            binding.detailTv.text = detail
            binding.sellerTv.text = seller
            binding.priceTv.text = price
        }

        //text에 언더라인 추가
        val degreeTv = binding.mannerDegreeTv
        val underlineText = SpannableString(degreeTv.text)
        underlineText.setSpan(UnderlineSpan(),0,underlineText.length,0)
        degreeTv.text = underlineText

    }
    //뒤로가기 버튼 이벤트 설정
    private fun backBtnOnClick(){
        binding.backBtnIv.setOnClickListener {
            finish()
        }

    }
}