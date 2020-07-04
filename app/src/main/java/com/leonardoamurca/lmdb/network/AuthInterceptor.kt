package com.leonardoamurca.lmdb.network

import com.leonardoamurca.lmdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url()
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        req = req.newBuilder().url(url).build()

        return chain.proceed(req)
    }
}