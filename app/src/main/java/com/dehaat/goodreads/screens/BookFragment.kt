package com.dehaat.goodreads.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dehaat.goodreads.adapters.AuthorAdapter
import com.dehaat.goodreads.adapters.BookAdapter
import com.dehaat.goodreads.databinding.FragmentBookBinding
import com.dehaat.goodreads.viewmodels.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class BookFragment : Fragment() {

    private lateinit var binding: FragmentBookBinding
    private val viewModel: BookViewModel by viewModels()
    private val args: BookFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = BookAdapter()
        binding.bookList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: BookAdapter) {
        viewModel.books(args.authorId)
            .subscribeBy(
                onError = {},
                onSuccess = { adapter.submitList(it) }
            )
    }

}