package com.dehaat.goodreads.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.dehaat.goodreads.R
import com.dehaat.goodreads.databinding.ActivityLoginBinding
import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.utils.GlobalConfig.Settings.VALID_PASSWORD
import com.dehaat.goodreads.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    companion object {
        const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    @Inject lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (loginViewModel.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            return
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)


    }

    override fun onResume() {
        super.onResume()

        binding = ActivityLoginBinding.bind(findViewById(android.R.id.content))
        attachTextWatcher()
        attachCallback()
    }

    private fun attachCallback() {
        binding.callback = object : Callback {
            override fun login() {
                if (binding.editTextEmail.text.toString().matches(emailPattern.toRegex())) {
                    if (binding.editTextPassword.text.trim() == VALID_PASSWORD) {
                        loginViewModel.performLogin(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()).subscribeBy(onError = {}, onSuccess = {
                            startActivity(Intent(binding.root.context, MainActivity::class.java))
                        })

                    } else binding.editTextPassword.error = resources.getString(R.string.error_incorrect_password)
                } else binding.editTextEmail.error = resources.getString(R.string.error_invalid_email)
            }
        }
    }

    private fun attachTextWatcher() {
        binding.editTextEmail.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            s?.let {
                loginViewModel.enableLogin = (it.isNotBlank() && binding.editTextPassword.text.isNotBlank())
            }
        })

        binding.editTextPassword.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            s?.let {
                loginViewModel.enableLogin = (it.isNotBlank() && binding.editTextEmail.text.isNotBlank())
            }
        })
    }

    interface Callback {
        fun login()
    }
}