package com.whitedev.bkf.model

import android.support.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class Data(
    @Nullable
    @SerializedName("datacell")
    val datacell: List<Datacell>
)