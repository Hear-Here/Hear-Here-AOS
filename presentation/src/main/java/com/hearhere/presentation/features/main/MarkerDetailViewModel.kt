package com.hearhere.presentation.features.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.usecaseImpl.GetPostUseCaseImpl
import com.hearhere.domain.usecaseImpl.PatchPostUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.common.component.emojiButton.WithType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MarkerDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPostUseCase: GetPostUseCaseImpl,
    private val patchPostUseCase: PatchPostUseCaseImpl
) : BaseViewModel() {

    val POST_ID = "postId"

    private val _uiState = MutableLiveData<MarkerDetailUiState>()
    val uiState: LiveData<MarkerDetailUiState> get() = _uiState

    var postId = 0L
        get() = savedStateHandle.get<Long>(POST_ID) ?: -1L

    private var toggleJob: Job? = null

    init {
        getMarkerDetail()
    }

    private fun getMarkerDetail() {
        _loading.postValue(true)
        // TODO

        viewModelScope.launch {
            getPostUseCase.getPost(postId).also {
                when (it) {
                    is ApiResponse.Success -> {
                        val res = it.data!!
                        val state = MarkerDetailUiState(
                            postId = res.postId,
                            writer = res.writer,
                            title = res.title,
                            artist = res.artist,
                            cover = Uri.parse(res.cover),
                            message = res.content,
                            genreType = GenreType.valueOf(res.genre),
                            weatherType = WeatherType.valueOf(res.weather),
                            withType = WithType.valueOf(res.whoWith),
                            emotionType = EmotionType.valueOf(res.mood),
                            distance = res.distance,
                            isLiked = res.isLike,
                            likeCnt = res.likeCount
                        )
                        _uiState.postValue(state)
                        _loading.postValue(false)
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun onClickLikeToggle() {
        if (uiState.value?.isLiked == true) {
            _uiState.postValue(
                uiState.value?.copy(
                    isLiked = !uiState.value!!.isLiked,
                    likeCnt = uiState.value!!.likeCnt - 1
                )
            )
        } else {
            _uiState.postValue(
                uiState.value?.copy(
                    isLiked = !uiState.value!!.isLiked,
                    likeCnt = uiState.value!!.likeCnt + 1
                )
            )
        }

        // 디바운스 처리
        toggleJob?.cancel()
        toggleJob = viewModelScope.launch {
            delay(500)
            // API
            Log.d("bottom toggle", uiState.value?.isLiked.toString())
            sendLikeState()
        }
    }

    fun sendLikeState() {
        viewModelScope.launch {
            if (uiState.value?.isLiked == true) {
                patchPostUseCase.likePost(uiState.value!!.postId)
            } else {
                patchPostUseCase.disLikePost(uiState.value!!.postId)
            }
        }
    }

    data class MarkerDetailUiState(
        val postId: Long,
        val writer: String,
        val title: String,
        val artist: String,
        val cover: Uri?,
        val message: String?,
        val genreType: GenreType?,
        val weatherType: WeatherType?,
        val withType: WithType?,
        val emotionType: EmotionType?,
        val distance: Double,
        val isLiked: Boolean,
        val likeCnt: Int,
    ) {
        val distanceStr = distance.toString()
        val likeCntStr = likeCnt.toString()
    }
}
