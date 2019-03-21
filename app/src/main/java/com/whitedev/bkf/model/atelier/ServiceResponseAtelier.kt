package com.whitedev.bkf.model.atelier

import android.support.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class ServiceResponseAtelier(
    @Nullable
    @SerializedName("list")
    val list: List<Atelier>,
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