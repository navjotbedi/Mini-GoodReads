package com.dehaat.goodreads.network.response

import com.squareup.moshi.Json

data class LoginResponse(@Json(name = "token") val token: String)