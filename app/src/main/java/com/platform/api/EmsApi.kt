package com.platform.api

import com.platform.data.response.UserDataList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface EmsApi {

    companion object{
        const val BASE_URL= "http://10.0.2.2:8080/EMSPortal/"
        const val DOCUMENT_ATTACHMENT_PARENT = "document"
    }

    @FormUrlEncoded
    @POST("auth")
    @Headers("Accept: application/json")
    fun login(@Field("j_username") login: String, @Field("j_password") password: String): Call<ResponseBody>

    @GET("rest/serviceorders")
    fun getServiceOrders(): Call<ResponseBody>

    @GET("rest/users")
        fun getUser(): Call<UserDataList>
}