package com.hearhere.presentation.post

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityPostBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostActivity : BaseActivity<ActivityPostBinding>(R.layout.activity_post) {

    private val viewModel : PostViewModel by viewModels()
//
//    private val fragmentManager = supportFragmentManager
//    private lateinit var transaction: FragmentTransaction

//    lateinit var adapter : BaseAdapter
//    private lateinit var viewPager: ViewPager2

    override fun onCreateView(savedInstanceState: Bundle?) {
//        initAdapter()
        val pagerAdapter = ViewPagerAdapter(this)
        val tabName = listOf("Title", "Artist")
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = "${(tabName[position])}"
        }.attach()


//        setViewPager()

//        binding.viewModel = viewModel
//        transaction = fragmentManager.beginTransaction()
//        transaction.add(R.id.frameLayout, SearchSingerFragment())
//        transaction.commit()
//        setViewPager()

    }

//    private fun initAdapter() {
//        adapter = BaseAdapter.build()
//    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {

    }
    private fun setViewPager() {
        val viewPager2 = binding.viewPager

        val adapter = ViewPagerAdapter(this)
//        adapter.setList()
        viewPager2.adapter = adapter
    }
}