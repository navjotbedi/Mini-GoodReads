package com.dehaat.goodreads.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.dehaat.goodreads.R
import com.dehaat.goodreads.databinding.ActivityLoginBinding
import com.dehaat.goodreads.manager.PreferenceManager
import com.dehaat.goodreads.utils.GlobalConfig.Settings.VALID_PASSWORD
import com.dehaat.goodreads.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

/**
 * Activity to perform login
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    companion object {
        const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    @Inject lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            return
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding = ActivityLoginBinding.bind(findViewById(R.id.root))
        attachTextWatcher()
        attachCallback()
    }

    private fun attachCallback() {
        binding.callback = object : Callback {
            override fun login() {
                if (binding.editTextEmail.text.toString().matches(emailPattern.toRegex())) {
                    if (binding.editTextPassword.text.toString() == VALID_PASSWORD) {
                        viewModel.performLogin(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()).subscribeBy(onError = {}, onSuccess = {
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
                viewModel.enableLogin.postValue(it.isNotBlank() && binding.editTextPassword.text.isNotBlank())
            }
        })

        binding.editTextPassword.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            s?.let {
                viewModel.enableLogin.postValue(it.isNotBlank() && binding.editTextEmail.text.isNotBlank())
            }
        })

        viewModel.enableLogin.observe(this, Observer<Boolean> {
            binding.buttonLogin.isEnabled = it
        })
    }

    interface Callback {
        fun login()
    }
}