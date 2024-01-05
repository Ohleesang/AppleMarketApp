package com.example.applemarketapp.data

import android.content.Context
import org.apache.poi.ss.usermodel.Cell
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
        } catch (e: Exception) {
        }
    }
}