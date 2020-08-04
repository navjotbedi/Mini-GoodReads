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
import com.dehaat.goodreads.databinding.ListItemAuthorBinding
import com.dehaat.goodreads.viewmodels.AuthorViewModel
import com.dehaat.goodreads.viewmodels.MainViewModel

/**
 * Adapter to control list of authors
 */
class AuthorAdapter(private val listener: OnClickListener?,
                    private val dualMode: Boolean,
                    private val mainViewModel: MainViewModel) : ListAdapter<AuthorViewModel, AuthorAdapter.ViewHolder>(AuthorDiffCallback()) {

    interface OnClickListener {
        fun onAuthorClicked(authorId: Long)
    }

    interface OnClickReadMore {
        fun onReadMoreClicked()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_author, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), mainViewModel.checkedPosition == position && dualMode)
    }

    inner class ViewHolder(private val binding: ListItemAuthorBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.readMoreClickListener = object : OnClickReadMore {
                override fun onReadMoreClicked() {
                    binding.textViewAuthorBio.maxLines = Int.MAX_VALUE
                    binding.textViewAuthorBio.text = binding.viewModel?.author?.bio
                    binding.buttonReadMore.visibility = View.INVISIBLE
                    getItem(adapterPosition).isReadMoreVisible = false
                }
            }

            binding.clickListener = object : OnClickListener {
                override fun onAuthorClicked(authorId: Long) {
                    if (dualMode) {
                        mainViewModel.select(authorId)
                        if (mainViewModel.checkedPosition != adapterPosition) {
                            val oldPosition = mainViewModel.checkedPosition
                            mainViewModel.checkedPosition = adapterPosition
                            notifyItemChanged(oldPosition)
                            notifyItemChanged(mainViewModel.checkedPosition)
                        }
                    } else {
                        mainViewModel.select(authorId)
                        listener?.onAuthorClicked(authorId)
                        mainViewModel.checkedPosition = adapterPosition
                    }
                }
            }
        }

        fun bind(authorViewModel: AuthorViewModel, selected: Boolean) {
            with(binding) {
                viewModel = authorViewModel
                rootView.setBackgroundResource(if (selected) R.color.colorLightGrey else R.color.colorWhite)
                executePendingBindings()
                textViewAuthorBio.maxLines = if (authorViewModel.isReadMoreVisible) 3 else Int.MAX_VALUE
                if (authorViewModel.isReadMoreVisible) {
                    binding.textViewAuthorBio.post {
                        binding.buttonReadMore.visibility = if (binding.textViewAuthorBio.lineCount > 3) View.VISIBLE else View.INVISIBLE
                    }
                }
            }
        }

    }
}

private class AuthorDiffCallback : DiffUtil.ItemCallback<AuthorViewModel>() {

    override fun areItemsTheSame(oldItem: AuthorViewModel, newItem: AuthorViewModel): Boolean {
        return oldItem.author.id == newItem.author.id
    }

    override fun areContentsTheSame(oldItem: AuthorViewModel, newItem: AuthorViewModel): Boolean {
        return oldItem.author == newItem.author
    }
}