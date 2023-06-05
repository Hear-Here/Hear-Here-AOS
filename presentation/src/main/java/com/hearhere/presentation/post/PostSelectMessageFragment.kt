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
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.databinding.FragmentPostSelectMessageBinding

class PostSelectMessageFragment
    : BaseFragment<FragmentPostSelectMessageBinding>(R.layout.fragment_post_select_message) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()

    private lateinit var adapter: BaseAdapter

    override fun initView() {
        binding.context = this


    }
    val onClickPostMessage = View.OnClickListener {

        viewModel.message = binding.messageEt.text.toString()
        Log.d("옥채연", viewModel.message.toString())
    }

    val onClickPostWithoutMessage = View.OnClickListener {
        viewModel.message = null
        Log.d("옥채연", viewModel.message.toString())
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = BaseAdapter.build()

    }
}