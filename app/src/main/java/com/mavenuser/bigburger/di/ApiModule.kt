package com.mavenuser.bigburger.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mavenuser.bigburger.data.api.BurgerListApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://legacy.vibuy.com/dump/"

@Module
class ApiModule {

    @Provides
    fun provideBurgerListApi(retrofit: Retrofit) : BurgerListApi{
        return retrofit.create(BurgerListApi::class.java)
    }

    @Provides
    fun providRetorfit(client : OkHttpClient) = Retrofit.Builder().baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory
            .create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
        .client(client).build()


    @Provides
    fun  provideClient(): OkHttpClient{

        val loggerInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient
            .Builder()
            .addInterceptor(loggerInterceptor)
            .build()
    }
}