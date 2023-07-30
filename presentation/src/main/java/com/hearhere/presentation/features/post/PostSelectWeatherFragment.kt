package com.hearhere.presentation.features.post

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseFragment
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.databinding.FragmentPostSelectWeatherBinding

class PostSelectWeatherFragment :
    BaseFragment<FragmentPostSelectWeatherBinding>(R.layout.fragment_post_select_weather) {

    private val viewModel: PostSelectOptionViewModel by activityViewModels()



    override fun initView() {
        binding.context = this
        binding.viewModel = viewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
