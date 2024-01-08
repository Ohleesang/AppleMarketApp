package com.example.applemarketapp


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarketapp.data.Item
import com.example.applemarketapp.data.ItemList
import com.example.applemarketapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val recyclerView by lazy{binding.itemListRv}
    private val linearLayoutManager = LinearLayoutManager(this)

    private lateinit var resultDetailLauncher: ActivityResultLauncher<Intent>

    private var savedPosition: Int = 0
    private var isScrolledStart = false

    //알림
    private val notification = Notification(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //더미 데이터 최초 생성
        setDummyData("dummy_data.xlsx")


        //알림 채널 생성
        notification.createNotificationChannel()

        //뷰를 생성
        createView()

        //콜백 함수들 정의
        setOnCallBackFunction()

    }


    //화면이 재생성 되었을때
    override fun onResume() {
        super.onResume()
        createView()
    }

    //액티비티 가 넘겨 가질때 현재 액티비티는 정지상태
    override fun onPause() {
        super.onPause()

        // 해당 액티비티의 위치를 찾기
        binding.itemListRv.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)

    }

    // 인스턴스 값 넣기
    private fun setDummyData(fileName: String) {
        //인스턴스 값 넣기
        ItemList.setDummyData(this, fileName)
    }

    // 뒤로가기 버튼 눌렀을때,
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




    //뷰를 생성 하는 부분
    private fun createView() {

        setContentView(binding.root)

        //RecyclerView 생성 하기
        setRecyclerView(ItemList.value)

        //저장된 위치에 보여주기
        binding.itemListRv.scrollToPosition(savedPosition)

    }


    //RecyclerView 생성
    private fun setRecyclerView(mList: MutableList<Item>) {


        recyclerView.layoutManager = linearLayoutManager

        val adapter = ItemAdapter(mList)
        recyclerView.adapter = adapter


        //인터페이스 정의
        //아이템 리스트 클릭시 이벤트 처리
        setItemOnclick(binding.itemListRv.adapter)



    }

    //콜백 함수들 정의
    private fun setOnCallBackFunction() {

        //스크롤 변동시 이벤트 처리
        setRecyclerViewOnScrolled()

        //벨 클릭시 이벤트 처리
        setOnTouchBell()

        //플로핑 버튼 클릭시 처리
        setOnTouchFloating()

        //결과값 받는 이벤트 처리
        resultDetailLauncher()

    }




    // 플로팅 버튼 터치시 처리
    private fun setOnTouchFloating() {
        val floatingBtn = binding.floatingBtnIv
        floatingBtn.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    floatingBtn.setImageResource(R.drawable.img_arrow_up_clicked)

                }

                MotionEvent.ACTION_UP -> {
                    floatingBtn.setImageResource(R.drawable.img_arrow_up)
                    //처음 위치로
                    binding.itemListRv.smoothScrollToPosition(0)
                }
            }

            true
        }
    }

    // 리사이클 뷰의 자식들 클릭했을때 처리
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


    // 스크롤 변동될때 생기는 메소드
    private fun setRecyclerViewOnScrolled(){

        //스크롤 변동될때 생기는 메소드
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // 스크롤 발생 시 호출되는 메서드
                // dx: 수평 스크롤의 변화량, dy: 수직 스크롤의 변화량


                // 플로팅 버튼 표시 여부 지정
                val floatingBtn = binding.floatingBtnIv
                val isFirstItemVisible =
                    linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0
                if (isFirstItemVisible){
//                    floatingBtn.visibility = View.INVISIBLE
                    applyFadeAnimation(floatingBtn,true)
                    isScrolledStart = false
                }
                  else{
                      if(!isScrolledStart) {
                          applyFadeAnimation(floatingBtn,false)
                          isScrolledStart = true
                      }
                }
            }

            //스크롤 상태에 따라 기능 실행
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    savedPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                }

            }
        })

    }


    // 알람 버튼 터치시 처리
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


                }
            }
    }

    // 해당 뷰에 애니메이션 처리 isFadeOut = true 이면 페이드아웃 flase이면 페이드인
    private fun applyFadeAnimation(view: View,isFadeOut:Boolean) {

        val fade : AlphaAnimation = if(isFadeOut) {
            AlphaAnimation(1.0f,0.0f)
        } else AlphaAnimation(0.0f,1.0f)

        //fade Out
        fade.duration = 200 // 페이드 아웃 지속 시간 단위 ms 1000 = 1초
        fade.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                if(isFadeOut) view.visibility = View.INVISIBLE // 페이드 아웃 후 뷰를 숨김
                else view.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })


        view.startAnimation(fade)

    }
}

