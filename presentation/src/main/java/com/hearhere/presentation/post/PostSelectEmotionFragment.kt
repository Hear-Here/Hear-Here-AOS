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
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.databinding.FragmentPostSelectEmotionBinding

class PostSelectEmotionFragment : BaseFragment<FragmentPostSelectEmotionBinding>(R.layout.fragment_post_select_emotion) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()

    private lateinit var adapter: BaseAdapter

    override fun initView() {
        binding.context = this

        binding.smileBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            Log.d("옥채연", it.isSelected.toString())

            viewModel.emotion = EmotionType.SMILE
            pageSlide()
        }
        binding.sosoBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            Log.d("옥채연", it.isSelected.toString())

            viewModel.emotion = EmotionType.SOSO
            pageSlide()
        }
        binding.sadBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            Log.d("옥채연", it.isSelected.toString())

            viewModel.emotion = EmotionType.SAD
            pageSlide()
        }
        binding.funnyBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            Log.d("옥채연", it.isSelected.toString())

            viewModel.emotion = EmotionType.FUNNY
            pageSlide()
        }
        binding.heartBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            Log.d("옥채연", it.isSelected.toString())

            viewModel.emotion = EmotionType.HEART
            pageSlide()
        }
        binding.angryBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            Log.d("옥채연", it.isSelected.toString())

            viewModel.emotion = EmotionType.ANGRY
            pageSlide()
        }
    }


    private fun pageSlide() {
        (requireActivity() as PostSelectOptionActivity).slidePage()
    }
}