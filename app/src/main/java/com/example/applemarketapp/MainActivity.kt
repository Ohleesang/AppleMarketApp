package com.example.applemarketapp


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.data.ItemList
import com.example.applemarketapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var resultDetailLauncher: ActivityResultLauncher<Intent>

    //알림
    private val notification = Notification(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //더미 데이터 최초 생성
        setDummyData("dummy_data.xlsx")

        //뷰를 생성
        createView()

        //결과값 처리
        resultDetailLauncher()

    }

    //데이터 결과 처리
    private fun resultDetailLauncher() {
        resultDetailLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    //데이터 받기
                    val data = result.data
                    val position = data?.getIntExtra("position", 0)
                    val item = data?.getParcelableExtra<Item>("item")

                    //데이터 처리
                    if (position != null && item != null) ItemList.value[position] = item
                    createView()
                }
            }
    }

    //뷰를 생성 하는 부분
    private fun createView() {

        setContentView(binding.root)

        //RecyclerView 생성 하기
        setRecyclerView(binding.itemListRv, ItemList.value)

        //아이템 리스트 클릭시 이벤트 처리
        setItemOnclick(binding.itemListRv.adapter)

        //알림 채널 생성
        notification.createNotificationChannel()

        //벨 클릭시 이벤트 처리
        setOnTouchBell()

        //플로핑 버튼 클릭시 처리
        setOnTouchFloating()

    }

    private fun setOnTouchFloating() {
        val btn = binding.floatingBtnIv
        btn.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    btn.setImageResource(R.drawable.img_arrow_up_clicked)
                    //처음 위치로
                    binding.itemListRv.smoothScrollToPosition(0)
                }

                MotionEvent.ACTION_UP -> {
                    btn.setImageResource(R.drawable.img_arrow_up)
                    //이벤트 발생!
                }
            }

            true
        }
    }

    //뒤로가기 버튼 눌렀을때,
    override fun onBackPressed() {

        //다이얼로그 실행
        val builder = AlertDialog.Builder(this)
        builder.run {
            setTitle("종료")
            setMessage("앱을 종료하시겠습니까?")
            setIcon(R.drawable.img_chat)
            setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }
            setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }

    }

    private fun setDummyData(fileName: String) {
        //인스턴스 값 넣기
        ItemList.setDummyData(this, fileName)
    }

    private fun setRecyclerView(recyclerView: RecyclerView, mList: MutableList<Item>) {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val adapter = ItemAdapter(mList)
        recyclerView.adapter = adapter


        //스크롤 변동될때 생기는 메소드
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // 스크롤 발생 시 호출되는 메서드
                // dx: 수평 스크롤의 변화량, dy: 수직 스크롤의 변화량
                val floatingBtn = binding.floatingBtnIv
                val isFirstItemVisible =
                    linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0
                if (isFirstItemVisible)
                    floatingBtn.visibility = View.INVISIBLE
                else floatingBtn.visibility = View.VISIBLE
            }
        })

    }

    private fun setItemOnclick(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {
        (adapter as ItemAdapter).itemClick = object : ItemAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(view.context, DetailActivity::class.java)

                //해당 뷰의 데이터를 전달
                val data = ItemList.value[position]
                intent.putExtra("clickedItem", data)
                intent.putExtra("position", position)
                resultDetailLauncher.launch(intent)
            }

            override fun onLongClick(view: View, position: Int) {
                //해당 여부를 묻는 다이얼로그생성

                //다이얼로그 실행
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.run {
                    setTitle("상품 삭제")
                    setMessage("상품을 정말로 삭제하시겠습니까?")
                    setIcon(R.drawable.img_chat)
                    setPositiveButton("확인") { dialog, _ ->

                        //확인시 해당 리스트값 제거
                        ItemList.deleteData(position)

                        //데이터가 변경되었으면 화면을 다시 구성
                        createView()

                        dialog.dismiss()
                    }
                    setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                    show()
                }

            }
        }
    }

    private fun setOnTouchBell() {
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
}