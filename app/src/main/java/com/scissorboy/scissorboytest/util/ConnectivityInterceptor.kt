package com.scissorboy.scissorboytest.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
       if (!isOnline(context)) {
           throw NoConnectivityException()
       }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}