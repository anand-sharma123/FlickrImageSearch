package com.task.flickrimagesearch.datasource.network

import com.task.flickrimagesearch.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppRepository{
    private var retrofit: Retrofit? = null

    fun createService() : FlickerService {
        if(retrofit === null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!.create(FlickerService::class.java)
    }
}