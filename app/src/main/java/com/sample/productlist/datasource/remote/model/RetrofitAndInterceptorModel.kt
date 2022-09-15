package com.sample.productlist.datasource.remote.model

import com.sample.productlist.datasource.remote.interceptor.DownloadProgressInterceptor
import retrofit2.Retrofit


data class RetrofitAndInterceptorModel(
    val retrofit: Retrofit,
    val downloadProgressInterceptor: DownloadProgressInterceptor
)
