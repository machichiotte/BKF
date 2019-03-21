package com.whitedev.bkf.model

import android.support.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class X(
    @Nullable
    @SerializedName("color")
    val color: String,
    @Nullable
    @SerializedName("data")
    val `data`: List<Data>,
    @Nullable
    @SerializedName("display")
    val display: Boolean,
    @Nullable
    @SerializedName("week")
    val week: String
)