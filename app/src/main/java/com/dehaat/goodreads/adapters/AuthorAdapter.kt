package com.dehaat.goodreads.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dehaat.goodreads.R
import com.dehaat.goodreads.databinding.ListItemAuthorBinding
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.screens.AuthorFragmentDirections
import com.dehaat.goodreads.viewmodels.AuthorViewModel

class AuthorAdapter : ListAdapter<Author, AuthorAdapter.ViewHolder>(AuthorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_author,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemAuthorBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.viewModel?.id?.let { authorId ->
                    val direction =
                        AuthorFragmentDirections.actionAuthorFragmentToBookFragment(authorId)
                    view.findNavController().navigate(direction)
                }
            }
        }

        fun bind(author: Author) {
            with(binding) {
                viewModel = author
                executePendingBindings()
            }
        }

    }
}

private class AuthorDiffCallback : DiffUtil.ItemCallback<Author>() {

    override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean {
        return oldItem == newItem
    }
}