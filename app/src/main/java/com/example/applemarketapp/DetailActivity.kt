package com.example.applemarketapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val position by lazy { intent.getIntExtra("position", 0) }
    private lateinit var item: Item

    private val onHeartResId = R.drawable.img_all_like
    private val offHeartResId = R.drawable.img_all_no_like

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
            intent.getParcelableExtra<Item>("clickedItem") ?: Item()
        item.run {
            binding.itemIv.setImageResource(imgResource)

            binding.itemNameTv.text = name
            binding.addressTv.text = address
            binding.detailTv.text = detail
            binding.sellerTv.text = seller
            binding.priceTv.text = price


            //체크 되어 있다면 이미지 변경
            if(isCheckedLike) binding.heartIv.setImageResource(onHeartResId)
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
            val returnIntent = Intent()
            returnIntent.putExtra("position", position)
            returnIntent.putExtra("item", item)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }

    private fun heartOnClick() {
        val heartIv = binding.heartIv

        heartIv.setOnClickListener {

            if (!item.isCheckedLike) { // 하트 Off
                //1. img 변경
                heartIv.setImageResource(onHeartResId)

                //2. 좋아요 수 증가
                item.like++
                //3. 스낵바 표시
                showSnackBar(getString(R.string.snack_bar_msg))
            } else { // 하트 On

                //1. img 변경
                heartIv.setImageResource(offHeartResId)
                //2. 좋아요 수 감소
                item.like--
            }

            item.isCheckedLike = !item.isCheckedLike
        }
    }

    private fun showSnackBar(message: String) {
        // 스낵바 생성
        val snackBar = Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        )
        // 스낵바 표시
        snackBar.show()
    }
}
