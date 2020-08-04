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

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dehaat.goodreads.R
import com.dehaat.goodreads.adapters.BookAdapter
import com.dehaat.goodreads.databinding.FragmentBookBinding
import com.dehaat.goodreads.utils.Utils
import com.dehaat.goodreads.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Fragment to show the list of Books
 */
@AndroidEntryPoint
class BookFragment : Fragment(R.layout.fragment_book) {

    @Inject lateinit var bookAdapter: BookAdapter
    @Inject lateinit var utils: Utils

    private lateinit var binding: FragmentBookBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        binding.bookList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        (binding.bookList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.bookList.adapter = bookAdapter

        if (utils.isDualMode()) {
            viewModel.authorSelected.observe(viewLifecycleOwner, Observer<Long> { subscribeUi(it) })
        } else {
            subscribeUi(viewModel.authorSelected.value)
        }
    }

    private fun subscribeUi(authorId: Long?) {
        authorId?.let { id ->
            viewModel.getBooks(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        viewModel.bookViewModels = it
                        (binding.bookList.adapter as BookAdapter).submitList(viewModel.bookViewModels)
                    },
                    onError = { utils.showToast(R.string.error_something_wrong) })
        }
    }

}