package com.dehaat.goodreads.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.dehaat.goodreads.R
import com.dehaat.goodreads.adapters.AuthorAdapter
import com.dehaat.goodreads.databinding.FragmentAuthorBinding
import com.dehaat.goodreads.viewmodels.AuthorViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


@AndroidEntryPoint
class AuthorFragment : Fragment(R.layout.fragment_author) {

    private lateinit var binding: FragmentAuthorBinding
    private val viewModel: AuthorViewModel by viewModels()
    private var listener: AuthorAdapter.OnClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthorBinding.bind(view)

        initUI()
    }

    private fun initUI(){
        binding.authorList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val adapter = AuthorAdapter(listener)
        binding.authorList.adapter = adapter
        subscribeUi(adapter)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is AuthorAdapter.OnClickListener){
            listener = context
        }
    }

    private fun subscribeUi(adapter: AuthorAdapter) {
        viewModel.authors
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {},
                onSuccess = {
                    binding.viewSwitcher.displayedChild = 1
                    adapter.submitList(it)
                }
            )
    }

}