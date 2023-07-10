package com.hearhere.presentation.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.chipButton.ChipClickListener
import com.hearhere.presentation.common.component.chipButton.ChipType
import com.hearhere.presentation.common.component.chipButton.FilterChipButton
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.common.component.emojiButton.WithType
import com.hearhere.presentation.common.component.emojiButton.getResource
import com.hearhere.presentation.features.main.adapter.FilterChipItemBinder
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostFilterViewModel @Inject constructor() : BaseViewModel() {

    private val _selectedGenre = MutableLiveData(
        initGenre()
    )
    val selectedGenre get() = _selectedGenre

    private val _selectedWith = MutableLiveData(
        initWith()
    )
    val selectedWith get() = _selectedWith

    private val _selectedWeather = MutableLiveData(
        initWeather()
    )
    val selectedWeather get() = _selectedWeather

    private val _selectedEmotion = MutableLiveData(
        initEmotion()
    )
    val selectedEmotion get() = _selectedEmotion

    private val _chipBinders = MutableLiveData<List<BaseItemBinder>>()
    val chipBinders get() = _chipBinders

    fun initFilterChips() {
        val genres = initGenre()
        val withs = initWith()
        val weathers = initWeather()
        val emotions = initEmotion()

        chipBinders.value?.forEach {
            it as FilterChipItemBinder
            when (it.chipType.get()) {
                ChipType.GENRE -> {
                    genres.first { state -> state.label == it.typeName.get() }.apply { isSelected = true }
                }

                ChipType.WITH -> {
                    withs.first { state -> state.label == it.typeName.get() }.apply { isSelected = true }
                }

                ChipType.WEATHER -> {
                    weathers.first { state -> state.label == it.typeName.get() }.apply { isSelected = true }
                }

                ChipType.EMOTION -> {
                    emotions.first { state -> state.label == it.typeName.get() }.apply { isSelected = true }
                }

                else -> {}
            }
        }
        _selectedGenre.postValue(genres)
        _selectedWith.postValue(withs)
        _selectedWeather.postValue(weathers)
        _selectedEmotion.postValue(emotions)
    }

    private fun initGenre() = mutableListOf(
        ChipState(GenreType.BALLAD.name),
        ChipState(GenreType.DANCE.name),
        ChipState(GenreType.HIPHOP.name),
        ChipState(GenreType.RB.name),
        ChipState(GenreType.INDIE.name),
        ChipState(GenreType.BAND.name),
        ChipState(GenreType.POP.name),
        ChipState(GenreType.TROT.name)
    )

    private fun initWeather() = mutableListOf(
        ChipState(WeatherType.SUNNY.name),
        ChipState(WeatherType.NORMAL.name),
        ChipState(WeatherType.CLOUDY.name),
        ChipState(WeatherType.RAINY.name),
        ChipState(WeatherType.WINDY.name),
        ChipState(WeatherType.SNOWY.name)
    )

    private fun initWith() = mutableListOf(
        ChipState(WithType.ALONE.name),
        ChipState(WithType.FRIEND.name),
        ChipState(WithType.COUPLE.name),
        ChipState(WithType.FAMILY.name),
        ChipState(WithType.PET.name),
        ChipState(WithType.SOMEBODY.name)
    )

    private fun initEmotion() = mutableListOf(
        ChipState(EmotionType.SMILE.name),
        ChipState(EmotionType.SOSO.name),
        ChipState(EmotionType.SAD.name),
        ChipState(EmotionType.FUNNY.name),
        ChipState(EmotionType.HEART.name),
        ChipState(EmotionType.ANGRY.name)
    )

    val onClickChip = object : ChipClickListener {
        override fun onClickChip(type: ChipType, text: String) {
            when (type) {
                ChipType.GENRE -> {
                    selectedGenre.value!!.first { state -> state.label == text }.apply { isSelected = !isSelected }
                    _selectedGenre.postValue(selectedGenre.value!!)
                }

                ChipType.WITH -> {
                    selectedWith.value!!.first { state -> state.label == text }.apply { isSelected = !isSelected }
                    _selectedWith.postValue(selectedWith.value!!)
                }

                ChipType.WEATHER -> {
                    selectedWeather.value!!.first { state -> state.label == text }.apply { isSelected = !isSelected }
                    _selectedWeather.postValue(selectedWeather.value!!)
                }

                ChipType.EMOTION -> {
                    selectedEmotion.value!!.first { state -> state.label == text }.apply { isSelected = !isSelected }
                    _selectedEmotion.postValue(selectedEmotion.value!!)
                }
            }
        }
    }

    private fun setFilters() {
        val filterList = mutableSetOf<FilterChipItemBinder>()
        selectedGenre.value?.filter { it.isSelected }?.forEach {
            val item = FilterChipItemBinder(onClickDeleteChip)
            item.setChipType(ChipType.GENRE)
            item.setChipTypeName(it.label)
            item.setChipText(GenreType.valueOf(it.label).kor)
            filterList.add(item)
        }

        selectedWith.value?.filter { it.isSelected }?.forEach {
            val item = FilterChipItemBinder(onClickDeleteChip)
            item.setChipType(ChipType.WITH)
            item.setChipTypeName(it.label)
            item.setChipText(WithType.valueOf(it.label).kor)
            filterList.add(item)
        }
        selectedEmotion.value?.filter { it.isSelected }?.forEach {
            val item = FilterChipItemBinder(onClickDeleteChip)
            item.setChipType(ChipType.EMOTION)
            item.setChipTypeName(it.label)
            item.setChipText(EmotionType.valueOf(it.label).kor)
            item.setChipEmoji(EmotionType.valueOf(it.label).getResource())
            filterList.add(item)
        }
        selectedWeather.value?.filter { it.isSelected }?.forEach {
            val item = FilterChipItemBinder(onClickDeleteChip)
            item.setChipType(ChipType.WEATHER)
            item.setChipTypeName(it.label)
            item.setChipText(WeatherType.valueOf(it.label).kor)
            item.setChipEmoji(WeatherType.valueOf(it.label).getResource())
            filterList.add(item)
        }
        filterList.sortedWith(compareBy { (it as FilterChipItemBinder).chipType.get() })

        viewModelScope.launch {
        }
        _chipBinders.postValue(filterList.toList())
    }

    fun onClickApply() {
        setFilters()
    }

    fun onClickClear() {
        _selectedGenre.postValue(initGenre())
        _selectedEmotion.postValue(initEmotion())
        _selectedWith.postValue(initWith())
        _selectedWeather.postValue(initWeather())
    }

    private val onClickDeleteChip = object : FilterChipButton.FilterChipClickListener {
        override fun onClick(type: ChipType, label: String) {
            when (type) {
                ChipType.GENRE -> {
                    selectedGenre.value!!.first { state -> state.label == label }.apply { isSelected = false }
                    _selectedGenre.postValue(selectedGenre.value)
                }

                ChipType.WITH -> {
                    selectedWith.value!!.first { state -> state.label == label }.apply { isSelected = false }
                    _selectedWith.postValue(selectedWith.value)
                }

                ChipType.WEATHER -> {
                    selectedWeather.value!!.first { state -> state.label == label }.apply { isSelected = false }
                    _selectedWeather.postValue(selectedWeather.value)
                }

                ChipType.EMOTION -> {
                    selectedEmotion.value!!.first { state -> state.label == label }.apply { isSelected = false }
                    _selectedEmotion.postValue(selectedEmotion.value)
                }
            }
            val temp = ArrayList<BaseItemBinder>()
            temp.addAll(chipBinders.value ?: emptyList())
            if (temp.isEmpty()) return

            temp.removeIf { (it as FilterChipItemBinder).chipType.get() == type && it.typeName.get() == label }
            temp.sortWith(compareBy { (it as FilterChipItemBinder).chipType.get() })
            _chipBinders.postValue(temp)
        }
    }

    data class ChipState(val label: String, var isSelected: Boolean = false)
}
