package com.hearhere.presentation.post

import android.os.Bundle
import androidx.activity.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityPostSelectOptionBinding
import com.hearhere.presentation.post.PostSelectOptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostSelectOptionActivity : BaseActivity<ActivityPostSelectOptionBinding>(R.layout.activity_post_select_option) {

    private val viewModel: PostSelectOptionViewModel by viewModels()
    private lateinit var adapter : BaseAdapter

    override fun onCreateView(savedInstanceState: Bundle?) {
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
    }

}