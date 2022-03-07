package com.platform.data.response


import com.google.gson.annotations.SerializedName

data class TaxTypesData(
    val columnValues: Any,
    val results: List<ResultX>,
    val totalCount: Int
)