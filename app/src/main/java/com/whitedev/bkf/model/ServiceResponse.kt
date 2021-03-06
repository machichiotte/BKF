package com.whitedev.bkf.model

import android.support.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    @Nullable
    @SerializedName("list")
    val list: List<X>,
    @Nullable
    @SerializedName("method")
    val method: String,
    @Nullable
    @SerializedName("status")
    val status: String,
    @Nullable
    @SerializedName("success_message")
    val success_message: String
)