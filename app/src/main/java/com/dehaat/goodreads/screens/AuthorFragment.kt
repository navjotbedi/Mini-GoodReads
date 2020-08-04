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

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dehaat.goodreads.R
import com.dehaat.goodreads.adapters.AuthorAdapter
import com.dehaat.goodreads.databinding.FragmentAuthorBinding
import com.dehaat.goodreads.utils.Utils
import com.dehaat.goodreads.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Fragment to show the list of Authors
 */
@AndroidEntryPoint
class AuthorFragment : Fragment(R.layout.fragment_author) {

    @Inject lateinit var utils: Utils
    private lateinit var binding: FragmentAuthorBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var listener: AuthorAdapter.OnClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthorBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        binding.authorList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.authorList.adapter = AuthorAdapter(listener, utils.isDualMode(), viewModel)
        (binding.authorList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        val listener = SwipeRefreshLayout.OnRefreshListener { subscribeUi(true) }
        binding.swipeRefresh.setOnRefreshListener(listener)
        if (viewModel.authorViewModels == null) listener.onRefresh() else subscribeUi(false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AuthorAdapter.OnClickListener) {
            listener = context
        }
    }

    private fun subscribeUi(forceUpdate: Boolean) {
        if (viewModel.authorViewModels == null || forceUpdate) {
            binding.swipeRefresh.isRefreshing = true
            viewModel.getAuthors()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        viewModel.authorViewModels = it
                        if (it.isNotEmpty()) {
                            viewModel.select(it.first().author.id)
                            viewModel.checkedPosition = 0
                        }
                        binding.swipeRefresh.isRefreshing = false
                        updateUI()
                    },
                    onError = {
                        binding.swipeRefresh.isRefreshing = false
                        viewModel.authorViewModels?.let { updateUI() } ?: utils.showToast(R.string.error_something_wrong)
                    })
        } else {
            updateUI()
        }
    }

    private fun updateUI() {
        (binding.authorList.adapter as AuthorAdapter).submitList(viewModel.authorViewModels)
        if (utils.isDualMode()) binding.authorList.scrollToPosition(viewModel.checkedPosition)
    }
}