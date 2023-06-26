package com.hearhere.presentation.features.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.common.component.emojiButton.WithType
import com.hearhere.presentation.databinding.FragmentPostSelectWithBinding

class PostSelectWithFragment : BaseFragment<FragmentPostSelectWithBinding>(R.layout.fragment_post_select_with) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()

    private lateinit var adapter: BaseAdapter

    override fun initView() {

        binding.context = this
    }

    val onClickAlone = View.OnClickListener {
        viewModel.with = WithType.ALONE
        pageSlide()
    }

    val onClickFriend = View.OnClickListener {
        viewModel.with = WithType.FRIEND
        pageSlide()
    }

    val onClickCouple = View.OnClickListener {
        viewModel.with = WithType.COUPLE
        pageSlide()
    }

    val onClickFamily = View.OnClickListener {
        viewModel.with = WithType.FAMILY
        pageSlide()
    }

    val onClickPet = View.OnClickListener {
        viewModel.with = WithType.PET
        pageSlide()
    }

    val onClickSomebody = View.OnClickListener {
        viewModel.with = WithType.SOMEBODY
        pageSlide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = BaseAdapter.build()
    }

    private fun pageSlide() {
        (requireActivity() as PostSelectOptionActivity).slidePage()
    }
}
