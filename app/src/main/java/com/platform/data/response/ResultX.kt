package com.platform.data.response


import com.google.gson.annotations.SerializedName

data class ResultX(
    val contextable: Boolean,
    val createdAt: Long,
    val draft: Boolean,
    val enabled: Boolean,
    val entityDescription: String,
    val entityType: String,
    val id: Int,
    val name: String,
    val updatedAt: Long,
    val version: Int
)