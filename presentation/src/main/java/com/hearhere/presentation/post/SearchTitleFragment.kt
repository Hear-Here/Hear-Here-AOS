package com.hearhere.presentation.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.databinding.FragmentSearchTitleBinding

class SearchTitleFragment : BaseFragment<FragmentSearchTitleBinding>(R.layout.fragment_search_title) {

    private val viewModel: SearchTitleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initView()
        return binding.root
    }

    override fun initView() {

    }

}