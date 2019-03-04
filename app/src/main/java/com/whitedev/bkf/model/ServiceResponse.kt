package com.whitedev.bkf.model

data class ServiceResponse(
    val list: List<X>,
    val method: String,
    val status: String,
    val success_message: String
)