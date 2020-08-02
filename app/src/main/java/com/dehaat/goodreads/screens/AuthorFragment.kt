package com.dehaat.goodreads.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dehaat.goodreads.adapters.AuthorAdapter
import com.dehaat.goodreads.databinding.FragmentAuthorBinding
import com.dehaat.goodreads.viewmodels.AuthorViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class AuthorFragment : Fragment() {

    private lateinit var binding: FragmentAuthorBinding
    private val viewModel: AuthorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = AuthorAdapter()
        binding.authorList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: AuthorAdapter) {
        viewModel.authors
            .subscribeBy(
                onError = {},
                onSuccess = { adapter.submitList(it) }
            )
    }

}