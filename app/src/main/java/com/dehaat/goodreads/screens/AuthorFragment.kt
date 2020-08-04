package com.dehaat.goodreads.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dehaat.goodreads.R
import com.dehaat.goodreads.adapters.AuthorAdapter
import com.dehaat.goodreads.databinding.FragmentAuthorBinding
import com.dehaat.goodreads.utils.Utils
import com.dehaat.goodreads.viewmodels.AuthorListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


/**
 * Fragment to show the list of Authors
 */
@AndroidEntryPoint
class AuthorFragment : Fragment(R.layout.fragment_author) {

    @Inject lateinit var utils: Utils
    private lateinit var binding: FragmentAuthorBinding
    private val viewModel: AuthorListViewModel by viewModels()
    private var listener: AuthorAdapter.OnClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthorBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        binding.authorList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.authorList.adapter = AuthorAdapter(listener, utils.isDualMode())
        (binding.authorList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        val listener = SwipeRefreshLayout.OnRefreshListener { subscribeUi() }
        binding.swipeRefresh.setOnRefreshListener(listener)
        listener.onRefresh()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AuthorAdapter.OnClickListener) {
            listener = context
        }
    }

    private fun subscribeUi() {
        binding.swipeRefresh.isRefreshing = true
        viewModel.authors.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(onError = { binding.swipeRefresh.isRefreshing = false }, onSuccess = {
            binding.swipeRefresh.isRefreshing = false
            (binding.authorList.adapter as AuthorAdapter).submitList(it)
        })
    }

}