package com.dehaat.goodreads.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dehaat.goodreads.databinding.FragmentBookBinding
import com.dehaat.goodreads.viewmodels.BookViewModel

class BookFragment : Fragment() {

    private lateinit var binding: FragmentBookBinding
    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookBinding.inflate(inflater, container, false)

        return binding.root
    }

}