package com.hearhere.presentation.features.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Pin
import com.hearhere.domain.model.PostQuery
import com.hearhere.domain.usecaseImpl.GetPostUseCaseImpl
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
import com.hearhere.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostFilterViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCaseImpl
) : BaseViewModel() {

    private val CITY_HALL = LatLng(37.566667, 126.978427)
    private val DEFAULT_LOCATION = CITY_HALL

    val _myLocation = MutableLiveData<LatLng>(DEFAULT_LOCATION)
    val myLocation get() = _myLocation

    private val _events = MutableStateFlow<List<FilterEvent>>(emptyList())
    val event get() = _events.asStateFlow()

    val navigateToLogin = SingleLiveEvent<Unit>()

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

    private val _queryFilter = MutableLiveData<HashMap<String, ArrayList<String>>>()
    val queryFilter get() = _queryFilter

    fun setMyLocation(location: LatLng) {
        _myLocation.postValue(location)
    }

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

    fun requestFilterResult() {
        val filterList = mutableSetOf<FilterChipItemBinder>()
        val queryfilter = hashMapOf<String, ArrayList<String>>()
        val temp = ArrayList<String>()

        _loading.postValue(true)
        selectedGenre.value?.filter { it.isSelected }?.forEach {
            val item = FilterChipItemBinder(onClickDeleteChip)
            item.setChipType(ChipType.GENRE)
            item.setChipTypeName(it.label)
            item.setChipText(GenreType.valueOf(it.label).kor)
            filterList.add(item)
            temp.add(it.label)
        }
        queryfilter["genre"] = temp.clone() as ArrayList<String>
        temp.clear()

        selectedWith.value?.filter { it.isSelected }?.forEach {
            val item = FilterChipItemBinder(onClickDeleteChip)
            item.setChipType(ChipType.WITH)
            item.setChipTypeName(it.label)
            item.setChipText(WithType.valueOf(it.label).kor)
            filterList.add(item)
            temp.add(it.label)
        }

        queryfilter["with"] = temp.clone() as ArrayList<String>
        temp.clear()

        selectedEmotion.value?.filter { it.isSelected }?.forEach {
            val item = FilterChipItemBinder(onClickDeleteChip)
            item.setChipType(ChipType.EMOTION)
            item.setChipTypeName(it.label)
            item.setChipText(EmotionType.valueOf(it.label).kor)
            item.setChipEmoji(EmotionType.valueOf(it.label).getResource())
            filterList.add(item)
            temp.add(it.label)
        }
        queryfilter["emotion"] = temp.clone() as ArrayList<String>
        temp.clear()

        selectedWeather.value?.filter { it.isSelected }?.forEach {
            val item = FilterChipItemBinder(onClickDeleteChip)
            item.setChipType(ChipType.WEATHER)
            item.setChipTypeName(it.label)
            item.setChipText(WeatherType.valueOf(it.label).kor)
            item.setChipEmoji(WeatherType.valueOf(it.label).getResource())
            filterList.add(item)
            temp.add(it.label)
        }

        queryfilter["weather"] = temp.clone() as ArrayList<String>
        temp.clear()

        _queryFilter.postValue(queryfilter)

        filterList.sortedWith(compareBy { (it as FilterChipItemBinder).chipType.get() })
        _chipBinders.postValue(filterList.toList())
    }

    fun queryClear() {
        _queryFilter.postValue(HashMap())
    }

    fun getFilterResult(lat: Double, lng: Double) {
        // Call API
        viewModelScope.launch {
            _loading.postValue(true)
            Log.d("queryFilter", queryFilter.value.toString())
            val query = PostQuery(
                lat = lat,
                lng = lng,
                emotionType = queryFilter.value?.get("emotion").toString().filterRegex(),
                withType = queryFilter.value?.get("with").toString().filterRegex(),
                genreType = queryFilter.value?.get("genre").toString().filterRegex(),
                weatherType = queryFilter.value?.get("weather").toString().filterRegex()
            )
            Log.d("query request", query.toString())
            getPostUseCase.getPostList(
                query
            ).also {
                _loading.postValue(false)
                when (it) {
                    is ApiResponse.Success -> {
                        addEvent(FilterEvent.OnCompleted(it.data!!))
                    }
                    is ApiResponse.Error -> {
                        _loading.postValue(false)
                        navigateToLogin.call()
                    }
                }
            }
        }
    }

    fun onClickApply() {
        this.requestFilterResult()
    }

    fun onClickClear() {
        _selectedGenre.postValue(initGenre())
        _selectedEmotion.postValue(initEmotion())
        _selectedWith.postValue(initWith())
        _selectedWeather.postValue(initWeather())
    }

    private val onClickDeleteChip = object : FilterChipButton.FilterChipClickListener {
        override fun onClick(type: ChipType, label: String) {
            // TODO 필터 query에 적용하기

            val query = if (queryFilter.value != null) queryFilter.value!! else hashMapOf()
            Log.d("delete query before", "${type.name} / ${label}" +query.toString())
            when (type) {
                ChipType.GENRE -> {
                    selectedGenre.value!!.first { state -> state.label == label }.apply { isSelected = false }
                    _selectedGenre.postValue(selectedGenre.value)
                    query["genre"] = query?.get("genre")?.filter { it != label } as ArrayList<String>
                }

                ChipType.WITH -> {
                    selectedWith.value!!.first { state -> state.label == label }.apply { isSelected = false }
                    _selectedWith.postValue(selectedWith.value)
                    query["with"] = query?.get("with")?.filter { it != label } as ArrayList<String>
                }

                ChipType.WEATHER -> {
                    selectedWeather.value!!.first { state -> state.label == label }.apply { isSelected = false }
                    _selectedWeather.postValue(selectedWeather.value)
                    query["weather"] = query?.get("weather")?.filter { it != label } as ArrayList<String>
                }

                ChipType.EMOTION -> {
                    selectedEmotion.value!!.first { state -> state.label == label }.apply { isSelected = false }
                    _selectedEmotion.postValue(selectedEmotion.value)
                    query["emotion"] = query?.get("emotion")?.filter { it != label } as ArrayList<String>
                }
            }
            val temp = ArrayList<BaseItemBinder>()
            temp.addAll(chipBinders.value ?: emptyList())

            temp.removeIf { (it as FilterChipItemBinder).chipType.get() == type && it.typeName.get() == label }
            temp.sortWith(compareBy { (it as FilterChipItemBinder).chipType.get() })
            Log.d("delete query", query.toString())
            _queryFilter.postValue(query)
            _chipBinders.postValue(temp)
        }
    }

    fun String.filterRegex(): String? {
        if (this == "[]") return null
        val regex = Regex("[^A-Za-z0-9,]")
        return regex.replace(this, "")
    }

    private fun addEvent(event: FilterEvent) {
        _events.update { it + event }
    }

    fun consumeViewEvent(event: FilterEvent) {
        _events.update { it - event }
    }

    data class ChipState(val label: String, var isSelected: Boolean = false)

    sealed class FilterEvent {
        data class OnCompleted(val list: List<Pin>) : FilterEvent()
    }
}
