package com.example.apachepoi.models

data class DataRows(
    val headers: MutableList<Any> = ArrayList(),
    val rows: MutableList<RowLine> = ArrayList()
)
