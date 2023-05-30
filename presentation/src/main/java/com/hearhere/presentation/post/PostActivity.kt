package com.hearhere.presentation.post

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityPostBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostActivity : BaseActivity<ActivityPostBinding>(R.layout.activity_post) {

    private val viewModel: PostViewModel by viewModels()
    private lateinit var adapter : BaseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        setViewPager()

        adapter = BaseAdapter.build()

        binding.searchBtn.setOnClickListener {
            viewModel.searchMusic(binding.searchEt.text.toString())
        }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.binder.observe {
            adapter.submitList(it)
            Log.d("binder", it.toString())
        }

    }

    private fun setViewPager() {
        val pagerAdapter = ViewPagerAdapter(this)
        val tabName = listOf("Title", "Artist")
            binding.viewPager.adapter = pagerAdapter

            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = (tabName[position])
        }.attach()
    }

}