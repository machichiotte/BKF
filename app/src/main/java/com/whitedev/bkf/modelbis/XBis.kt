package com.whitedev.bkf.modelbis

import com.whitedev.bkf.model.Data

data class XBis(
    val color: String,
    val `data`: List<Data>,
    val display: Boolean,
    val week: String
)