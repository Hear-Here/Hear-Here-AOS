package com.hearhere.presentation.post

import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.common.component.emojiButton.WithType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostSelectOptionViewModel @Inject constructor() : BaseViewModel() {

    var genre : GenreType? = null
    var with : WithType? = null
    var weather : WeatherType? = null
    var emotion : EmotionType? = null
    var message : String? = null



}