package com.platform.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CookiesLoggerInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headers = arrayOf("Cookie", "Host")
        for (header in headers) {
            val headerValue = request.header(header)
            if (headerValue != null) {
                Log.d(header, headerValue)
            } else {
                Log.d(header, "NONE")
            }
        }
        return chain.proceed(chain.request())
    }
}