package com.hearhere.presentation.post

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

    private lateinit var adapter: BaseAdapter

    override fun initView() {
        binding.context = this

        binding.sunnyBtn.setOnClickListener {
            it.isSelected = !it.isSelected

            viewModel.weather = WeatherType.SUNNY
            pageSlide()
        }

        binding.normalBtn.setOnClickListener {
            it.isSelected = !it.isSelected

            viewModel.weather = WeatherType.NORMAL
            pageSlide()
        }
        binding.cloudyBtn.setOnClickListener {
            it.isSelected = !it.isSelected

            viewModel.weather = WeatherType.CLOUDY
            pageSlide()
        }
        binding.rainyBtn.setOnClickListener {
            it.isSelected = !it.isSelected

            viewModel.weather = WeatherType.RAINY
            pageSlide()
        }
        binding.windyBtn.setOnClickListener {
            it.isSelected = !it.isSelected

            viewModel.weather = WeatherType.WINDY
            pageSlide()
        }
        binding.snowyBtn.setOnClickListener {
            it.isSelected = !it.isSelected

            viewModel.weather = WeatherType.SNOWY
            pageSlide()
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
