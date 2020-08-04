package com.dehaat.goodreads.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dehaat.goodreads.R
import com.dehaat.goodreads.databinding.ListItemAuthorBinding
import com.dehaat.goodreads.db.entity.Author

class AuthorAdapter(private val listener: OnClickListener?) : ListAdapter<Author, AuthorAdapter.ViewHolder>(AuthorDiffCallback()) {

    interface OnClickListener {
        fun onAuthorClicked(authorId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_author, parent, false), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemAuthorBinding, private val listener: OnClickListener?) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clickListener = object : OnClickListener {
                override fun onAuthorClicked(authorId: Long) {
                    listener?.onAuthorClicked(authorId)
                }
            }
        }

        fun bind(author: Author) {
            with(binding) {
                viewModel?.author = author
//                ReadMoreOption.addReadMoreTo(binding.textViewAuthorBio, author.bio)
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