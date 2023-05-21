package com.hearhere.presentation.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.databinding.FragmentSearchSingerBinding

class SearchSingerFragment : BaseFragment<FragmentSearchSingerBinding>(R.layout.fragment_search_singer) {

    private val viewModel: SearchSingerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState) // 이걸 안 해주면 오류남...
        initView()
        return binding.root
    }

    override fun initView() {
        Log.d("/Here", "shet")
    }

}