package com.hearhere.presentation.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.databinding.FragmentSearchSingerBinding

class SearchSingerFragment : BaseFragment<FragmentSearchSingerBinding>(R.layout.fragment_search_singer) {

    private val viewModel: PostViewModel by activityViewModels()

    private lateinit var adapter : BaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = BaseAdapter.build()

    }
    override fun initView() {

        binding.markerListRv.adapter = adapter
        viewModel.singerBinder.observe(requireActivity()) {
            adapter.submitList(it)
            Log.d("binder", it.toString())
        }

    }

}