package com.hearhere.presentation.features.post

import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.databinding.FragmentPostSelectEmotionBinding

class PostSelectEmotionFragment : BaseFragment<FragmentPostSelectEmotionBinding>(R.layout.fragment_post_select_emotion) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()

    override fun initView() {
        binding.context = this
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}
