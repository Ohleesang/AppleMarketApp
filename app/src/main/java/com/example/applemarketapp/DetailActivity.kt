package com.example.applemarketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.Toast
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.databinding.ActivityDetailBinding
import com.example.applemarketapp.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var item :Item
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setDataView()

        backBtnOnClick()

        heartOnClick()

    }


    //데이터를 받아서 화면을 재구성
    private fun setDataView() {
        item =
            intent.getParcelableExtra<Item>("clickedItem") ?: Item(0,"","","","","",0,0)
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
        underlineText.setSpan(UnderlineSpan(), 0, underlineText.length, 0)
        degreeTv.text = underlineText

    }

    //뒤로가기 버튼 이벤트 설정
    private fun backBtnOnClick() {
        binding.backBtnIv.setOnClickListener {
            finish()
        }

    }

    private fun heartOnClick() {
        val heartIv = binding.heartIv

        heartIv.setOnClickListener {

            //tag 값에 리소스 값을 저장하여 비교

            val onHeartResId = R.drawable.img_all_like
            val offHeartResId = R.drawable.img_all_no_like

            //처음 tag가 null이면 최초 상태이므로
            val heartTag = it.tag as? Int ?: offHeartResId



            if (heartTag == offHeartResId) { // 하트 Off
                //1. img 변경
                heartIv.setImageResource(onHeartResId)
                it.tag = onHeartResId

                //2. 좋아요 수 증가

                //3. 스낵바 표시

            } else if (heartTag == onHeartResId) { // 하트 On
                //1. img 변경
                heartIv.setImageResource(offHeartResId)
                it.tag = offHeartResId

                //2. 좋아요 수 감소
            }

        }
    }
}