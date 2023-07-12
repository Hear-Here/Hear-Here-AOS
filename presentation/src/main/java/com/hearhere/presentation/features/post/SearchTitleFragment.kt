package com.hearhere.presentation.features.post

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.databinding.FragmentSearchTitleBinding

class SearchTitleFragment :
    BaseFragment<FragmentSearchTitleBinding>(R.layout.fragment_search_title) {

    private val viewModel: PostViewModel by activityViewModels()

    private lateinit var adapter: BaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = BaseAdapter.build()
    }

    override fun initView() {
        binding.viewmodel = viewModel
        binding.markerListRv.adapter = adapter
        viewModel.titleBinder.observe(requireActivity()) {
            adapter.submitList(it)
            binding.charector.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
            Log.d("binder", it.toString())
        }
    }
}
