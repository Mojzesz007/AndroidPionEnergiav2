package com.platform.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.platform.EncryptedPreferencesProvider
import com.platform.api.EmsApi
import com.platform.utils.CookiesLoggerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ConnectionPool
import okhttp3.Cookie
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(EmsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(cookieJar: JavaNetCookieJar): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) //Logowanie response body
            //.addNetworkInterceptor(CookiesLoggerInterceptor())  //Logowanie cookies
            .cookieJar(cookieJar)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.MINUTES)
            .connectionPool(ConnectionPool(1, 30, java.util.concurrent.TimeUnit.SECONDS))
            .build()

    @Provides
    @Singleton
    fun provideJavaNetCookieJar(): JavaNetCookieJar {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return JavaNetCookieJar(cookieManager)
    }

    @Provides
    @Singleton
    fun provideEmsApi(retrofit: Retrofit): EmsApi = retrofit.create(EmsApi::class.java)

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): EncryptedPreferencesProvider {
        val encryptedSharedPreferences = EncryptedPreferencesProvider(context)
        return encryptedSharedPreferences
    }

}