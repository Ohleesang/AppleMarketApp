package com.example.applemarketapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.data.ItemList
import com.example.applemarketapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //알림 채널
    private val notification = Notification(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setDummyData("dummy_data.xlsx")

        //RecyclerView 생성 하기
        setRecyclerView(binding.itemListRv, ItemList.value)

        //아이템 리스트 클릭시 이벤트 처리
        setItemOnclick(binding.itemListRv.adapter)


        //알림 채널 생성
        notification.createNotificationChannel()

        //벨 클릭시 이벤트 처리
        val bellView = binding.bellIv
        bellView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    bellView.setImageResource(R.drawable.img_notice_bell)
                }

                MotionEvent.ACTION_UP -> {
                    bellView.setImageResource(R.drawable.img_notice)
                    //알람 이벤트 발생!
                    notification.showNotification()
                }
            }
            true
        }

    }

    private fun setDummyData(fileName: String) {
        //인스턴스 값 넣기
        ItemList.setDummyData(this, fileName)
    }

    private fun setRecyclerView(recyclerView: RecyclerView, mList: MutableList<Item>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(mList)
        recyclerView.adapter = adapter

    }

    private fun setItemOnclick(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {
        (adapter as ItemAdapter).itemClick = object : ItemAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(view.context, DetailActivity::class.java)

                //해당 뷰의 데이터를 전달
                val data = ItemList.value[position]
                intent.putExtra("clickedItem", data)
                startActivity(intent)
            }
        }
    }
}