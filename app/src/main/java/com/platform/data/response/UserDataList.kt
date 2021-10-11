package com.platform.data.response

import com.platform.data.response.Result

data class UserDataList(
    val columnValues: Any,
    val results: List<Result>,
    val totalCount: Int


)