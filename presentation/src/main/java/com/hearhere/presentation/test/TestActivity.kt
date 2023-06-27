package com.hearhere.presentation.test

import android.os.Bundle
import androidx.activity.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.databinding.ActivityTestBinding
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TestActivity : BaseActivity<ActivityTestBinding>(R.layout.activity_test) {

    private val viewModel: TestViewModel by viewModels()
    lateinit var adapter: BaseAdapter

    override fun onCreateView(savedInstanceState: Bundle?) {
        initAdapter()
        binding.viewmodel = viewModel

        // binding.testRv.adapter = adapter
    }

    fun initAdapter() {
        adapter = BaseAdapter.build()
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.list.observe {
            adapter.submitList(it)
        }
    }
}
