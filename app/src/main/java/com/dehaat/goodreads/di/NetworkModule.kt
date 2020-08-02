package com.dehaat.goodreads.di

import android.content.Context
import com.dehaat.goodreads.network.RestApi
import com.dehaat.goodreads.network.interceptors.RequestTokenInterceptor
import com.dehaat.goodreads.utils.GlobalConfig.Core.CACHE_NAME
import com.dehaat.goodreads.utils.GlobalConfig.Endpoint.URL_BASE
import com.dehaat.goodreads.utils.GlobalConfig.Settings.CACHE_SIZE
import com.dehaat.goodreads.utils.GlobalConfig.Settings.ENABLE_LOGS
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {

    @Provides
    fun provideRestService(okHttpClient: OkHttpClient, moshi: Moshi): RestApi {
        return configureRetrofit(okHttpClient, moshi).create(RestApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.addNetworkInterceptor(RequestTokenInterceptor())
        context.externalCacheDir?.let {
            builder.cache(Cache(File(it.absolutePath + "/" + CACHE_NAME), CACHE_SIZE))
        }

        if (ENABLE_LOGS) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun configureRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

}