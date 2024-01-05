package com.example.applemarketapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applemarketapp.data.ItemList
import com.example.applemarketapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){



    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //인스턴스 값 넣기
        ItemList.setDummyData(this,"dummy_data.xlsx")

        //RecyclerView 생성하기

        binding.itemListRv.layoutManager = LinearLayoutManager(this)

        var adapter = ItemAdapter(ItemList.value)
        binding.itemListRv.adapter = adapter

        //클릭시 이벤트처리
        adapter.itemClick = object :ItemAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(view.context,DetailActivity::class.java)

                //해당 뷰의 데이터를 전달
                val data = ItemList.value[position]
                intent.putExtra("clickedItem",data)
                startActivity(intent)
            }

        }
    }
}