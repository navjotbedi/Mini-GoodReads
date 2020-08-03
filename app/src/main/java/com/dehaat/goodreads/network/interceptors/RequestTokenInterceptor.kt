package com.dehaat.goodreads.network.interceptors

import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.utils.GlobalConfig.Network.HEADER_AUTH_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class RequestTokenInterceptor(private val preferenceManager: PreferenceManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (preferenceManager.isLoggedIn) builder.addHeader(
            HEADER_AUTH_TOKEN,
            preferenceManager.authToken!!
        )
        return chain.proceed(builder.build())
    }
}