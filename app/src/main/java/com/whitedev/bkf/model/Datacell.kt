package com.whitedev.bkf.model

import android.support.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class Datacell(
    @Nullable
    @SerializedName("data")
    val `data`: String?,
    @Nullable
    @SerializedName("display")
    val display: Boolean,
    @Nullable
    @SerializedName("name")
    val name: String
)