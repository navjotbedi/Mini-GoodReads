package com.dehaat.goodreads.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.dehaat.goodreads.R
import com.dehaat.goodreads.adapters.BookAdapter
import com.dehaat.goodreads.databinding.FragmentAuthorBinding
import com.dehaat.goodreads.databinding.FragmentBookBinding
import com.dehaat.goodreads.utils.GlobalConfig.DB.Book.COLUMN_AUTHOR_ID
import com.dehaat.goodreads.viewmodels.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class BookFragment : Fragment(R.layout.fragment_book) {

    private lateinit var binding: FragmentBookBinding
    private val viewModel: BookViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookBinding.bind(view)

        initUI()
    }

    private fun initUI(){
        binding.bookList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val adapter = BookAdapter()
        binding.bookList.adapter = adapter
        arguments?.let {
            val authorId = it.getLong(COLUMN_AUTHOR_ID)
            if (authorId > 0) subscribeUi(authorId)
        }
    }

    fun subscribeUi(authorId: Long) {
        viewModel.books(authorId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {},
                onSuccess = { (binding.bookList.adapter as BookAdapter).submitList(it) }
            )
    }

}