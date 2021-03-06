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

package com.dehaat.goodreads.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dehaat.goodreads.R
import com.dehaat.goodreads.databinding.ListItemBookBinding
import com.dehaat.goodreads.viewmodels.BookViewModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

/**
 * Adapter to control list of books
 */
@FragmentScoped
class BookAdapter @Inject constructor() : ListAdapter<BookViewModel, BookAdapter.ViewHolder>(BookDiffCallback()) {

    interface OnClickReadMore {
        fun onReadMoreClicked()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_book, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.readMoreClickListener = object : OnClickReadMore {
                override fun onReadMoreClicked() {
                    binding.textViewBookDescription.maxLines = Int.MAX_VALUE
                    binding.textViewBookDescription.text = binding.viewModel?.description
                    binding.buttonReadMore.visibility = View.INVISIBLE
                    getItem(adapterPosition).isReadMoreVisible = false
                }
            }
        }

        fun bind(bookViewModel: BookViewModel) {
            with(binding) {
                viewModel = bookViewModel
                executePendingBindings()
                binding.textViewBookDescription.post {
                    binding.buttonReadMore.visibility = if (binding.textViewBookDescription.lineCount > 3 && bookViewModel.isReadMoreVisible) View.VISIBLE else View.INVISIBLE
                }
            }
        }

    }
}

private class BookDiffCallback : DiffUtil.ItemCallback<BookViewModel>() {

    override fun areItemsTheSame(oldItem: BookViewModel, newItem: BookViewModel): Boolean {
        return oldItem.book.id == newItem.book.id
    }

    override fun areContentsTheSame(oldItem: BookViewModel, newItem: BookViewModel): Boolean {
        return oldItem.book.id == newItem.book.id
    }
}