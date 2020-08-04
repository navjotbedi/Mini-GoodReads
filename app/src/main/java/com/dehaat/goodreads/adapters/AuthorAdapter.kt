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
import com.dehaat.goodreads.db.entity.Author
import com.dehaat.goodreads.viewmodels.AuthorViewModel

/**
 * Adapter to control list of authors
 */
class AuthorAdapter(private val listener: OnClickListener?, private val dualMode: Boolean) : ListAdapter<Author, AuthorAdapter.ViewHolder>(AuthorDiffCallback()) {

    private var checkedPosition = -1

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
        holder.bind(getItem(position), checkedPosition == position)
    }

    inner class ViewHolder(private val binding: ListItemAuthorBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.readMoreClickListener = object : OnClickReadMore {
                override fun onReadMoreClicked() {
                    binding.textViewAuthorBio.maxLines = Int.MAX_VALUE
                    binding.textViewAuthorBio.text = binding.viewModel?.author?.bio
                    binding.buttonReadMore.visibility = View.INVISIBLE
                }
            }

            binding.clickListener = object : OnClickListener {
                override fun onAuthorClicked(authorId: Long) {
                    listener?.onAuthorClicked(authorId)
                    if (dualMode) {
                        if (checkedPosition != adapterPosition) {
                            val oldPosition = checkedPosition
                            checkedPosition = adapterPosition
                            notifyItemChanged(oldPosition)
                            notifyItemChanged(checkedPosition)
                        }
                    }
                }
            }
        }

        fun bind(author: Author, selected: Boolean) {
            with(binding) {
                viewModel = AuthorViewModel(author)
                rootView.setBackgroundResource(if (selected) R.color.colorLightGrey else R.color.colorWhite)
                executePendingBindings()
                binding.textViewAuthorBio.post {
                    binding.buttonReadMore.visibility = if (binding.textViewAuthorBio.lineCount > 3) View.VISIBLE else View.INVISIBLE
                }
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