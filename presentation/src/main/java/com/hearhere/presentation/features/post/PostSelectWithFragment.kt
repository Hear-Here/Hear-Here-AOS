package com.hearhere.presentation.features.post

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.databinding.FragmentPostSelectWithBinding

class PostSelectWithFragment : BaseFragment<FragmentPostSelectWithBinding>(R.layout.fragment_post_select_with) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()

    private lateinit var adapter: BaseAdapter

    override fun initView() {
        binding.context = this
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = BaseAdapter.build()
    }

    private fun pageSlide() {
        (requireActivity() as PostSelectOptionActivity).slidePage()
    }
}
