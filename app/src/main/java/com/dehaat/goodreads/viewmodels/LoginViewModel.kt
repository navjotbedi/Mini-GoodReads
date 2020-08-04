package com.dehaat.goodreads.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.network.RestApi
import com.dehaat.goodreads.network.request.LoginRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel @ViewModelInject constructor(private val preferenceManager: PreferenceManager, private val restApi: RestApi) : ViewModel() {

    var enableLogin: Boolean = false

    fun isLoggedIn(): Boolean {
        return preferenceManager.isLoggedIn
    }

    fun performLogin(email: String, password: String): Single<Unit> {
        return restApi.login(LoginRequest(email, password)).map { preferenceManager.authToken = it.token }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

}