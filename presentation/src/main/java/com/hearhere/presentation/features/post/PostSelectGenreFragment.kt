package com.hearhere.presentation.features.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.GenreWideButton
import com.hearhere.presentation.databinding.FragmentPostSelectGenreBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostSelectGenreFragment :
    BaseFragment<FragmentPostSelectGenreBinding>(R.layout.fragment_post_select_genre) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
    override fun initView() {
        binding.context = this
        binding.viewModel = viewModel
    }

}
