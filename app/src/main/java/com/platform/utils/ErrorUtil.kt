package com.platform.utils

import com.platform.pojo.APIError
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class ErrorUtil @Inject internal constructor(private val retrofit: Retrofit) {
    fun parseError(response: Response<*>): APIError? {
        val converter = retrofit.responseBodyConverter<APIError>(
            APIError::class.java, arrayOfNulls(0)
        )
        val error: APIError
        error = try {
            converter.convert(response.errorBody())!!
        } catch (e: IOException) {
            return APIError()
        }
        return error
    }
}