package com.platform.api

import com.platform.pojo.UserProfile
import com.platform.pojo.contractors.Contractors
import com.platform.pojo.contracts.Contracts
import com.platform.pojo.costInvoice.CostInvoice
import com.platform.pojo.costInvoices.CostInvoices
import com.platform.pojo.currencies.Currencies
import com.platform.pojo.employees.Employees
import com.platform.pojo.sellInvoices.SellInvoices
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET





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
    //---------------------------------------------CONTRACTS---------------------------------------------
    @GET("rest/contracts/listAllEnding")
    fun getContracts(
        @Query("max") max :Integer,
        @Query("start") start :Integer
    ): Call<Contracts>

    @GET("rest/contracts/endIn90daysEnding")
    fun getContractsEndingin90days(
        @Query("max") max :Integer,
        @Query("start") start :Integer
    ): Call<Contracts>
    //---------------------------------------------SELL INVOICES---------------------------------------------
    @GET("rest/sellinvoices")
    fun getSellInvoices(
        @Query("max") max :Integer,
        @Query("start"
        ) start :Integer): Call<SellInvoices>

    @GET("rest/sellinvoices?notpaid=true")
    fun getUnpaidSellInvoices(
        @Query("max") max :Integer,
        @Query("start") start :Integer
    ): Call<SellInvoices>
    //---------------------------------------------COST INVOICES---------------------------------------------
    @GET("rest/costinvoices")
    fun getCostInvoices(
        @Query("max") max :Integer,
        @Query("start"
        ) start :Integer): Call<CostInvoices>

    @GET("rest/costinvoices?notpaid=true")
    fun getUnpaidCostInvoices(
        @Query("max") max :Integer,
        @Query("start") start :Integer
    ): Call<CostInvoices>

    @GET("rest/costinvoices/{id}")
    fun getCostInvoiceById(
        @Path("id") id: Int?
    ): Call<CostInvoice>

    @PUT("rest/costinvoices/{id}")
    fun UpdateCostInvoice(
        @Path("id") id: Int?,
        @Body costInvoice :CostInvoice?
    ): Call<ResponseBody>

    @GET("rest/currencies?max=50")
    fun getCurrencies(
    ): Call<Currencies>

    @GET("rest/contractors?branch=true&max=50&start=0")
    fun getContractors(
    ): Call<Contractors>

    @GET("rest/users/employees?max=10&start=0")
    fun getEmployees(
    ): Call<Employees>



}