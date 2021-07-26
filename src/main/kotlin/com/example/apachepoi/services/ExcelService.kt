package com.example.apachepoi.services

import com.example.apachepoi.models.DataRows
import java.io.ByteArrayInputStream

interface ExcelService {

    fun uploadExcelFile(byteFile: ByteArray): DataRows

    fun downloadExcelFile(): ByteArrayInputStream
}