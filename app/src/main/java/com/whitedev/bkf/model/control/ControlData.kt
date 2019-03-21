package com.whitedev.bkf.model.control

data class ControlData(
    val order : String,
    val comment : String,
    val encodedList: MutableList<String>
)