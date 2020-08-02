package com.dehaat.goodreads.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dehaat.goodreads.databinding.FragmentAuthorBinding
import com.dehaat.goodreads.viewmodels.AuthorViewModel

class AuthorFragment : Fragment() {

    private lateinit var binding: FragmentAuthorBinding
    private val authorViewModel: AuthorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorBinding.inflate(inflater, container, false)

        return binding.root
    }

}