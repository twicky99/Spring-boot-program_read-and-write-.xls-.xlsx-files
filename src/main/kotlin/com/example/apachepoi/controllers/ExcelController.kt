package com.example.apachepoi.controllers

import com.example.apachepoi.models.DataRows
import com.example.apachepoi.services.ExcelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.IOException

@Controller
class ExcelController @Autowired constructor(private val excelService: ExcelService) {

    private var data: DataRows = DataRows()

    @GetMapping
    fun downloadPage(model: Model): String {
        model.addAttribute("data", data)
        return "test"
    }

    @GetMapping("/download")
    @ResponseBody
    fun downloadExcel(): ResponseEntity<InputStreamResource> {
        val excelFile: ByteArrayInputStream = excelService.downloadExcelFile()
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "attachment; filename=test.xlsx")
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(InputStreamResource(excelFile))
    }

    @PostMapping("/upload")
    @Throws(IOException::class)
    fun uploadExcel(@RequestPart("file") file: MultipartFile): String {
        val filename = file.originalFilename ?: throw RuntimeException("Not valid file")
        val extensionIndex = filename.lastIndexOf(".")
        if (extensionIndex == -1) {
            throw RuntimeException("Not valid file")
        }
        val extension = filename.substring(extensionIndex)
        if (extension != ".xls" && extension != ".xlsx") {
            throw RuntimeException("Not valid file")
        }
        data = excelService.uploadExcelFile(file.bytes)
        return "redirect:/"
    }
}