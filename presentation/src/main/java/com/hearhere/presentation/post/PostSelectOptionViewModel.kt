package com.hearhere.presentation.post

import com.hearhere.domain.model.Posting
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.common.component.emojiButton.WithType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostSelectOptionViewModel @Inject constructor() : BaseViewModel() {

    var cover: String? = null
    var artist: String? = null
    var title: String? = null
    var songId: Long? = null
    val temp = 0
    var genre: GenreType? = null
    var with: WithType? = null
    var weather: WeatherType? = null
    var emotion: EmotionType? = null
    var message: String? = null
    var posting: Posting? = null

    fun postPosting() {
        posting = Posting(
            songId = songId!!,
            title = title!!,
            artist = artist!!,
            cover = cover,
            genreType = genre!!.name,
            withType = with!!.name,
            temp = temp,
            weatherType = weather!!.name,
            emotionType = emotion!!.name,
            content = message,
            longitude = null,
            latitude = null
        )
    }
}
