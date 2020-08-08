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

package com.dehaat.goodreads.screens

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo.IME_ACTION_GO
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.dehaat.goodreads.R
import com.dehaat.goodreads.databinding.ActivityLoginBinding
import com.dehaat.goodreads.utils.GlobalConfig.Settings.VALID_EMAIL
import com.dehaat.goodreads.utils.GlobalConfig.Settings.VALID_PASSWORD
import com.dehaat.goodreads.utils.Utils
import com.dehaat.goodreads.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_login.*
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

    @Inject lateinit var utils: Utils

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding = ActivityLoginBinding.bind(root)
        attachTextWatcher()
        attachCallback()
    }

    private fun attachCallback() {
        binding.callback = object : Callback {
            override fun login() {
                performLogin()
            }
        }

        binding.editTextPassword.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener if (actionId == IME_ACTION_GO && binding.buttonLogin.isEnabled) {
                binding.buttonLogin.performClick()
                true
            } else false
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

    private fun performLogin() {
        if (canLogin()) {
            viewModel.enableLogin.postValue(false)
            viewModel.performLogin(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString())
                .subscribeBy(
                    onError = {
                        viewModel.enableLogin.postValue(true)
                        utils.showToast(R.string.error_something_wrong)
                    },
                    onSuccess = {
                        viewModel.enableLogin.postValue(true)
                        utils.showToast(R.string.toast_login_success)
                        nextScreen()
                    }
                )
        }
    }

    private fun canLogin(): Boolean {
        if (binding.editTextEmail.text.toString().matches(emailPattern.toRegex())) {
            if (binding.editTextEmail.text.toString() == VALID_EMAIL) {
                if (binding.editTextPassword.text.toString() == VALID_PASSWORD) {
                    return true
                } else binding.editTextPassword.error = resources.getString(R.string.error_incorrect_password)
            } else binding.editTextEmail.error = resources.getString(R.string.error_incorrect_email)
        } else binding.editTextEmail.error = resources.getString(R.string.error_invalid_email)

        return false
    }

    private fun nextScreen() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    interface Callback {
        fun login()
    }
}