package com.hearhere.presentation.features.post

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.GenreWideButton
import com.hearhere.presentation.databinding.FragmentPostSelectGenreBinding

class PostSelectGenreFragment :
    BaseFragment<FragmentPostSelectGenreBinding>(R.layout.fragment_post_select_genre) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()

    private lateinit var adapter: BaseAdapter

    override fun initView() {
        binding.context = this
    }

    val onClickGenreBtn = object : GenreWideButton.GenreOnClickListener {
        override fun onClick(genre: GenreType) {
            pageSlide()
            viewModel.genre = genre
        }
    }

    private fun pageSlide() {
        (requireActivity() as PostSelectOptionActivity).slidePage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = BaseAdapter.build()
    }
}
