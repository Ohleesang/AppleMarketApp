package com.example.applemarketapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applemarketapp.data.ItemList
import com.example.applemarketapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //[임시] 액티비티 이동
        binding.ivBell.setOnClickListener {
            startActivity(Intent(this,DetailActivity::class.java))
        }

        //인스턴스 값 넣기
        ItemList.setDummyData(this,"dummy_data.xlsx")

        //RecyclerView 생성하기

        binding.itemListRv.layoutManager = LinearLayoutManager(this)

        var adapter = ItemAdapter(ItemList.value)
        binding.itemListRv.adapter = adapter


    }
}