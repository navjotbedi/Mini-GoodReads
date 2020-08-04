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

package com.dehaat.goodreads.network

import com.dehaat.goodreads.network.request.LoginRequest
import com.dehaat.goodreads.network.response.BooksResponse
import com.dehaat.goodreads.network.response.LoginResponse
import com.dehaat.goodreads.utils.GlobalConfig.Endpoint.URL_AUTHOR_REQUEST
import com.dehaat.goodreads.utils.GlobalConfig.Endpoint.URL_LOGIN_REQUEST
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * RESTApis containing all the APIs call signatures
 */
interface RestApi {

    @POST(URL_LOGIN_REQUEST)
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

    @GET(URL_AUTHOR_REQUEST)
    fun getBooks(): Single<BooksResponse>

}