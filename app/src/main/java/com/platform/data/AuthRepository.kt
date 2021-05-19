package com.platform.data

import com.platform.api.EmsApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val emsApi: EmsApi) {

    //Przykład repozytorium - to właśnie tutaj powinny znajdować się wywołania
    //EMSAPI - dane otrzymane powinny być obserwowalne
    //I podpięte w fragmencie/activity (wzorzec MVVM)

}

