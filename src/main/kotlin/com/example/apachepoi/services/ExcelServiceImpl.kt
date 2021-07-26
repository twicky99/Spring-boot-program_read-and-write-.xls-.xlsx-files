package com.example.apachepoi.services

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

@Service
class ExcelServiceImpl : ExcelService {

    private val headers: Array<String> = arrayOf("id", "first_name", "last_name")

    override fun downloadExcelFile(): ByteArrayInputStream {
        try {
            XSSFWorkbook().use { workbook ->
                ByteArrayOutputStream().use { out ->
                    val sheet: Sheet = workbook.createSheet("Test")
                    setHeader(workbook, sheet, headers)

                    // what ever you want in body
                    var rowId = 1
                    for (i in 0..99) {
                        val row = sheet.createRow(rowId++)
                        row.createCell(0).setCellValue((i + 1).toDouble())
                        row.createCell(1).setCellValue("Andie $i")
                        row.createCell(2).setCellValue("Coopers $i")
                    }
                    for (col in headers.indices) {
                        sheet.autoSizeColumn(col)
                    }
                    workbook.write(out)
                    return ByteArrayInputStream(out.toByteArray())
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("Cannot write excel file:", e)
        }
    }

    override fun uploadExcelFile(byteFile: ByteArray) {
        try {
            ByteArrayInputStream(byteFile).use { file ->
                XSSFWorkbook(file).use { workbook ->
                    for (i in 0 until workbook.numberOfSheets) {
                        val sheet: Sheet = workbook.getSheetAt(i)
                        for (row in sheet) {
                            for (cell in row) {
                                //what ever you want with this file
                                if (cell.cellType == CellType.STRING) { }
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("Cannot read excel file:", e)
        }
    }

    private fun setHeader(workbook: Workbook, sheet: Sheet, columns: Array<String>) {
        val headerFont = workbook.createFont()
        headerFont.bold = true
        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        // Row for Header
        val headerRow = sheet.createRow(0)


        // Header
        for (col in columns.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(columns[col])
            cell.cellStyle = headerCellStyle
        }
    }
}