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

package com.dehaat.goodreads.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.network.RestApi
import com.dehaat.goodreads.network.request.LoginRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel @ViewModelInject constructor(private val preferenceManager: PreferenceManager, private val restApi: RestApi) : ViewModel() {

    val enableLogin: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun performLogin(email: String, password: String): Single<Unit> {
        return restApi.login(LoginRequest(email, password))
            .map { preferenceManager.authToken = it.token }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

}