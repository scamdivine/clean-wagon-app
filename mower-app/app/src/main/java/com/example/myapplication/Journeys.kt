package com.example.myapplication

data class Journey (val id: Int = 0, val mowerID: Int = 0,
                    val startDate: String = "", val endDate: String = ""){}

data class Coordinates (val id: Int = 0, val x: Int = 0, val y: Int = 0, val isEvent: Int = 0){}