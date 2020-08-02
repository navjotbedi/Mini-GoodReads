package com.dehaat.goodreads.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.dehaat.goodreads.R
import com.dehaat.goodreads.databinding.ActivityLoginBinding
import com.dehaat.goodreads.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!loginViewModel.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            return
        }

        binding = setContentView(this, R.layout.activity_login)

        binding.callback = object : Callback {
            override fun login() {
                if (binding.editTextEmail.text.isNotBlank()) {
                    if (binding.editTextEmail.text.toString().matches(emailPattern.toRegex())) {
                        if (binding.editTextPassword.text.isNotBlank()) {
                            loginViewModel.performLogin()
                            startActivity(Intent(binding.root.context, MainActivity::class.java))
                        } else binding.editTextPassword.error = "Empty Password"
                    } else binding.editTextEmail.error = "Invalid Email"
                } else binding.editTextEmail.error = "Empty Email"
            }
        }
    }

    interface Callback {
        fun login()
    }
}