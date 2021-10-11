package com.platform.data.response

data class Result(
    val contextable: Boolean,
    val createdAt: Long,
    val currentPassword: Any,
    val draft: Boolean,
    val email: Any,
    val enabled: Boolean,
    val entityDescription: String,
    val entityType: String,
    val fullname: String,
    val id: Int,
    val initials: String,
    val language: String,
    val lastPasswordChangedDate: Long,
    val login: String,
    val name: String,
    val phone: Any,
    val recordsPerPage: Int,
    val role: String,
    val securityPrincipalName: String,
    val settings: Any,
    val surname: String,
    val updatedAt: Any,
    val verifyCode: Any,
    val version: Int
)