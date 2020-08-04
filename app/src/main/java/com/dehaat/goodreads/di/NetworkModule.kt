/*
 * Copyright (C) 2020 Navjot Singh Bedi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dehaat.goodreads.di

import android.content.Context
import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.network.RestApi
import com.dehaat.goodreads.network.interceptors.RequestTokenInterceptor
import com.dehaat.goodreads.network.utils.BackendDateAdapter
import com.dehaat.goodreads.utils.GlobalConfig.Core.CACHE_NAME
import com.dehaat.goodreads.utils.GlobalConfig.Endpoint.URL_BASE
import com.dehaat.goodreads.utils.GlobalConfig.Settings.CACHE_SIZE
import com.dehaat.goodreads.utils.GlobalConfig.Settings.ENABLE_LOGS
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRestService(okHttpClient: OkHttpClient, moshi: Moshi): RestApi {
        return configureRetrofit(okHttpClient, moshi).create(RestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context, preferenceManager: PreferenceManager): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.addNetworkInterceptor(RequestTokenInterceptor(preferenceManager))
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
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(BackendDateAdapter())
            .build()
    }

    private fun configureRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(MoshiConverterFactory.create(moshi)).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).client(okHttpClient).build()
    }

}