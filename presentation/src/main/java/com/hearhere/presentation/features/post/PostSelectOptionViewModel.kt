package com.hearhere.presentation.features.post

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.hearhere.domain.model.Posting
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.BasicButton
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.GenreWideButton
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.common.component.emojiButton.WithType
import com.hearhere.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostSelectOptionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    var cover: String? = savedStateHandle.get("music_cover") ?: null
    var artist: String? = savedStateHandle.get("music_artist") ?: null
    var title: String? = savedStateHandle.get("music_title") ?: null
    var songId: Long? = savedStateHandle.get("music_songId") ?: null
    val temp = 0
    var genre = MutableLiveData<GenreType?>(null)
    var with = MutableLiveData<WithType?>(null)
    var weather = MutableLiveData<WeatherType?>(null)
    var emotion = MutableLiveData<EmotionType?>(null)
    var message: String? = null
    var postingState = MutableLiveData(
        PostingState(
            Posting(
                songId = songId ?: -1,
                title = title ?: "",
                artist = artist ?: "",
                cover = cover ?: "",
                genreType = "",
                withType = "",
                temp = temp,
                weatherType = "",
                emotionType = "",
                content = "",
                longitude = null,
                latitude = null
            )
        )
    )
    val posting get() = postingState.value!!.posting

    val navigationSlide = SingleLiveEvent<Unit>()
    val navigationFinish = SingleLiveEvent<Posting>()
    data class PostingState(val posting: Posting) {
        val postingEnabled = !posting.genreType.isNullOrBlank() &&
            !posting.withType.isNullOrBlank() &&
            !posting.weatherType.isNullOrBlank() &&
            !posting.emotionType.isNullOrBlank()
    }

    fun updateMessage(msg: String?) {
        message = msg
        postingState.postValue(postingState.value?.copy(posting.copy(content = msg)))
    }

    val onClickGenreBtn = object : GenreWideButton.GenreOnClickListener {
        override fun onClick(genreType: GenreType) {
            genre.postValue(genreType)
            postingState.postValue(postingState.value?.copy(posting.copy(genreType = genreType.name)))
            navigationSlide.call()
        }
    }

    fun onClickEmotion(view: View, emotionType: EmotionType) {
        emotion.postValue(emotionType)
        postingState.postValue(postingState.value?.copy(posting.copy(emotionType = emotionType.name)))
        navigationSlide.call()
    }

    fun onClickWeather(weatherType: WeatherType) {
        weather.postValue(weatherType)
        postingState.postValue(postingState.value?.copy(posting.copy(weatherType = weatherType.name)))
        navigationSlide.call()
    }

    val onClickWith = object : BasicButton.onClickListener {
        override fun onClickWithText(s: String) {
            val withType = WithType.values().first { t -> t.kor == s }
            with.postValue(withType)
            postingState.postValue(postingState.value?.copy(posting.copy(withType = withType.name)))
            navigationSlide.call()
        }
    }
}
