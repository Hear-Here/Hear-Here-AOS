package com.hearhere.presentation.features.post

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.model.Posting
import com.hearhere.domain.usecaseImpl.PostPostingUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.common.component.emojiButton.WithType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostFinishViewModel @Inject constructor(
    val useCase: PostPostingUseCaseImpl
) : BaseViewModel() {
    private val _uiState = MutableLiveData<MarkerPostUiState>()
    val uiState: LiveData<MarkerPostUiState> get() = _uiState

    fun getDetail(posting: Posting) {

        val state = MarkerPostUiState(
            title = posting.title,
            artist = posting.artist,
            cover = Uri.parse(posting.cover),
            message = posting.content,
            genreType = GenreType.valueOf(posting.genreType),
            weatherType = WeatherType.valueOf(posting.weatherType),
            withType = WithType.valueOf(posting.withType),
            emotionType = EmotionType.valueOf(posting.emotionType)
        )
        _uiState.postValue(state)

        Log.d("옥채연", state.toString())
    }

    data class MarkerPostUiState(
        val title: String,
        val artist: String,
        val cover: Uri?,
        val message: String?,
        val genreType: GenreType?,
        val weatherType: WeatherType?,
        val withType: WithType?,
        val emotionType: EmotionType?
    )

    fun requestPost(posting: Posting) {
        Log.d("옥채연/requestPost", posting.toString())
        viewModelScope.launch {
            useCase.postMusicPosting(posting)
        }
    }
}
