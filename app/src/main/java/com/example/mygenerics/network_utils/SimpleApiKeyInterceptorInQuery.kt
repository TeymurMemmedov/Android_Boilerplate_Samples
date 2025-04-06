package com.example.mygenerics.network_utils

import okhttp3.Interceptor
import okhttp3.Response

class SimpleApiKeyInterceptorInQuery(
    val parameterName:String,
    val parameterValue:String?
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentUrl = chain.request().url()
        val newUrl = currentUrl.newBuilder().addQueryParameter(parameterName, parameterValue).build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(newUrl).build()
        return chain.proceed(newRequest)
    }
}