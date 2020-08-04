package com.dehaat.goodreads.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dehaat.goodreads.R
import com.dehaat.goodreads.databinding.ListItemBookBinding
import com.dehaat.goodreads.db.entity.Book
import com.dehaat.goodreads.utils.Utils
import com.dehaat.goodreads.viewmodels.BookViewModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

/**
 * Adapter to control list of books
 */
@FragmentScoped
class BookAdapter @Inject constructor() : ListAdapter<Book, BookAdapter.ViewHolder>(BookDiffCallback()) {

    @Inject lateinit var utils: Utils

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_book, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            with(binding) {
                viewModel = BookViewModel(book, utils)
                executePendingBindings()
            }
        }

    }
}

private class BookDiffCallback : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }
}