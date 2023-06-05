package com.hearhere.presentation.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.common.component.BasicButton
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.databinding.FragmentPostSelectGenreBinding

class PostSelectGenreFragment :
    BaseFragment<FragmentPostSelectGenreBinding>(R.layout.fragment_post_select_genre) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()

    private lateinit var adapter: BaseAdapter

    override fun initView() {
        binding.context = this
    }

    val onClickBallad = View.OnClickListener {

        Log.d("ok", "채연이")
        viewModel.genre = GenreType.BALLAD
        pageSlide()

    }

    private fun pageSlide() {
        (requireActivity() as PostSelectOptionActivity).slidePage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = BaseAdapter.build()

    }

}