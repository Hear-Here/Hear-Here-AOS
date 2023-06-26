package com.hearhere.presentation.features.post

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
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

    val onClickDance = View.OnClickListener {
        Log.d("ok", "채연이")
        viewModel.genre = GenreType.DANCE
        pageSlide()
    }
    val onClickHiphop = View.OnClickListener {
        Log.d("ok", "채연이")
        viewModel.genre = GenreType.HIPHOP
        pageSlide()
    }
    val onClickRB = View.OnClickListener {
        Log.d("ok", "채연이")
        viewModel.genre = GenreType.RB
        pageSlide()
    }
    val onClickIndie = View.OnClickListener {
        Log.d("ok", "채연이")
        viewModel.genre = GenreType.INDIE
        pageSlide()
    }
    val onClickPop = View.OnClickListener {
        Log.d("ok", "채연이")
        viewModel.genre = GenreType.POP
        pageSlide()
    }
    val onClickBand = View.OnClickListener {
        Log.d("ok", "채연이")
        viewModel.genre = GenreType.BAND
        pageSlide()
    }
    val onClickTrot = View.OnClickListener {
        Log.d("ok", "채연이")
        viewModel.genre = GenreType.TROT
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
