package com.hmelikyan.securemessenger.root.networking

import com.hmelikyan.securemessenger.App
import com.hmelikyan.securemessenger.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    private val cache: Cache = Cache(App.getInstance()!!.applicationContext.cacheDir, 10 * 1024 * 1024)
    private var cachedInstance: Retrofit? = null

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(Authenticator())
//        .addInterceptor(UnAuthorizedInterceptor())
        .cache(cache)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    fun getConnectionInstance(baseUrl: String = BuildConfig.BASEURL): Retrofit {
        if (cachedInstance == null) {
            cachedInstance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        }
        return cachedInstance!!
    }
}