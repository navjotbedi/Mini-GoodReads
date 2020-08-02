package com.dehaat.goodreads.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.network.RestApi

class LoginViewModel @ViewModelInject constructor(
    private val preferenceManager: PreferenceManager,
    private val restApi: RestApi,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun isLoggedIn(): Boolean {
        return preferenceManager.isLoggedIn
    }

    fun performLogin() {
        restApi.login()
        preferenceManager.isLoggedIn
    }

}