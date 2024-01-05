package com.example.applemarketapp.data

import android.content.Context
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream


object ItemList {
    val value = mutableListOf<Item>()
    fun setDummyData(context: Context, fileName: String) {
        try {
            //A. assets 폴더에 저장된 Excel 파일 열기
            val inputStream: InputStream = context.assets.open(fileName)
            val workbook = XSSFWorkbook(inputStream)
            val sheet = workbook.getSheetAt(0)


            //B. 해당 데이터를 읽어들인후 값을 저장
            val dataArray = mutableListOf<List<String>>()

            //각 행을 처리
            for (row: Row in sheet) {
                val rowData = mutableListOf<String>()

                // 셀의 개수에 따라 데이터를 배열에 추가
                for (cell in row) {
                    //셀이 비어있으면 무시
                    if (cell.cellType == CellType.BLANK) continue
                    rowData.add(cell.toString())
                }
                if (rowData.isNotEmpty()) dataArray.add(rowData)

            }
            //C .파일 닫기
            inputStream.close()

            //D . 인스턴스 생성 이후 값넣기

            // 설명부분 제거
            dataArray.removeFirst()

            //인스턴스에 값넣기
            dataArray.forEach { row ->

                //1. 이미지 리소스 파일을 찾아서 해당되는 값 찾기
                val resId = context.resources.getIdentifier(row[1], "drawable", context.packageName)

                //2. 세부사항 String 값 \\n -> \n

                val detail = row[3].replace("\\n","\n")


                "금고\\n떼서 가져가야함\\n대우월드마크센텀\\n미국이주관계로 싸게 팝니다\n"
                //3. String to Int
                val price = row[5].toFloat().toInt()
                val like = row[7].toFloat().toInt()
                val chat = row[8].toFloat().toInt()

                //4. etc
                val name = row[2]
                val seller = row[4]
                val address = row[6]

                // 인스턴스 생성!
                val item = Item(resId, name, detail, seller, price, address, like, chat)

                value.add(item)
            }
        } catch (_: Exception) {
        }
    }
}