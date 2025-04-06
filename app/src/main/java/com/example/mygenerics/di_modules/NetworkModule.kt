package com.example.mygenerics.di_modules

import com.example.mygenerics.network_utils.SimpleApiKeyInterceptorInQuery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val API_KEY =""

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SimpleApiKeyInterceptorInQuery("api_key", API_KEY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClientInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_KEY)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideService(retrofit: Retrofit): SomeApiService {
//        return retrofit.create(SomeApiService::class.java)
//    }
}