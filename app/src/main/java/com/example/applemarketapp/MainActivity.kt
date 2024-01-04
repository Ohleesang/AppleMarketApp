package com.example.applemarketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    object Itemlist {
        val value = mutableListOf<Item>()
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

    private fun setDummyData() {
        Itemlist.value.add(
            Item(
                R.drawable.sample1,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample2,
                "김치냉장고",
                "이사로인해 내놔요",
                "안마담",
                20000,
                "인천 계양구 귤현동",
                8,
                28
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample3,
                "샤넬 카드지갑",
                "고퀄지갑이구요\n사용감이 있어서 싸게 내어둡니다",
                "코코유",
                10000,
                "수성구 범어동",
                23,
                5
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample4,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample5,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample6,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample7,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample8,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample9,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
        Itemlist.value.add(
            Item(
                R.drawable.sample10,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
    }
}