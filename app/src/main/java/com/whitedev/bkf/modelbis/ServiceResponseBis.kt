package com.whitedev.bkf.modelbis

data class ServiceResponseBis(
    val list: List<XBis>,
    val method: String,
    val status: String,
    val success_message: String
)