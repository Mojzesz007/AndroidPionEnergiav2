package com.platform.api

import com.platform.pojo.UserProfile
import com.platform.pojo.contracts.Contracts
import com.platform.pojo.sellInvoices.SellInvoices
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface EmsApi {

    companion object{
        const val BASE_URL= "http://10.0.2.2:8080/EMSPionEnergia/"
        const val DOCUMENT_ATTACHMENT_PARENT = "document"
    }

    @FormUrlEncoded
    @POST("auth")
    @Headers("Accept: application/json")
    fun login(@Field("j_username") login: String, @Field("j_password") password: String): Call<ResponseBody>

    @GET("rest/serviceorders")
    fun getServiceOrders(): Call<ResponseBody>

    @GET("rest/userprofile")
        fun getEntityByLogin(): Call<UserProfile>
    //CONTRACTS
    @GET("rest/contracts/listAllEnding?max=100&start=0")
    fun getContracts(): Call<Contracts>

    @GET("rest/contracts/endIn90daysEnding?max=100&start=0")
    fun getContractsEndingin90days(): Call<Contracts>
    //SELL INVOICES
    @GET("rest/sellinvoices?max=100&start=0")
    fun getSellInvoices(): Call<SellInvoices>
    @GET("rest/sellinvoices?max=100&notpaid=true&start=0")
    fun getUnpaidSellInvoices(): Call<SellInvoices>

}