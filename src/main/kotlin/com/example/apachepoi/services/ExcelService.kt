package com.example.apachepoi.services

import java.io.ByteArrayInputStream

interface ExcelService {

    fun uploadExcelFile(byteFile: ByteArray)

    fun downloadExcelFile(): ByteArrayInputStream
}