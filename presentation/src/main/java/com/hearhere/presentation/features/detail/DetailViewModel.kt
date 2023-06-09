package com.hearhere.presentation.features.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostUseCase: GetPostUseCaseImpl,
    private val patchPostUseCase: PatchPostUseCaseImpl
) : BaseViewModel() {

    val POST_ID = "POST_ID"

    private val _uiState = MutableLiveData<PostingDetailUiState?>(null)
    val uiState: LiveData<PostingDetailUiState?> get() = _uiState

    var postId = savedStateHandle.get<Long>(POST_ID) ?: -1L

    private var toggleJob: Job? = null

    init {
        getMarkerDetail()
    }

    fun getMarkerDetail() {
        viewModelScope.launch {
            _loading.postValue(true)
            getPostUseCase.getPost(postId).also {
                when (it) {
                    is ApiResponse.Success -> {
                        val res = it.data!!
                        val state = PostingDetailUiState(
                            postId = res.postId,
                            writer = res.writer,
                            title = res.title,
                            artist = res.artist,
                            cover = Uri.parse(res.cover),
                            bitmap = loadUrlToBitmap(""),
                            message = res.content,
                            genreType = GenreType.valueOf(res.genre),
                            weatherType = WeatherType.valueOf(res.weather),
                            withType = WithType.valueOf(res.whoWith),
                            emotionType = EmotionType.valueOf(res.mood),
                            distance = res.distance,
                            isLike = res.isLike,
                            likeCnt = res.likeCount,
                            latitude = res.latitude,
                            longitude = res.longitude
                        )
                        Log.d("hyom - detail", state.toString())
                        _uiState.postValue(state)
                        _loading.postValue(false)
                    }
                    else -> {
                        Log.d("hyom - detail", it.message.toString())
                    }
                }
            }
        }
    }

    fun onClickLikeToggle() {
        if (uiState.value?.isLike == true) {
            _uiState.postValue(
                uiState.value?.copy(
                    isLike = !uiState.value!!.isLike,
                    likeCnt = uiState.value!!.likeCnt - 1
                )
            )
        } else {
            _uiState.postValue(
                uiState.value?.copy(
                    isLike = !uiState.value!!.isLike,
                    likeCnt = uiState.value!!.likeCnt + 1
                )
            )
        }

        // 디바운스 처리
        toggleJob?.cancel()
        toggleJob = viewModelScope.launch {
            delay(500)
            // API
            sendLikeState()
            Log.d("bottom toggle", uiState.value?.isLike.toString())
        }
    }

    fun sendLikeState() {
        viewModelScope.launch {
            if (uiState.value?.isLike == true) {
                patchPostUseCase.likePost(uiState.value!!.postId)
            } else {
                patchPostUseCase.disLikePost(uiState.value!!.postId)
            }
        }
    }

    private fun loadUrlToBitmap(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        val uThread: Thread = object : Thread() {
            override fun run() {
                try {
                    val conn = URL(url).openConnection() as HttpURLConnection
                    conn.doInput = true
                    conn.connect()
                    val `is` = conn.inputStream
                    bitmap = BitmapFactory.decodeStream(`is`)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        uThread.start()

        try {
            uThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return bitmap
    }

    data class PostingDetailUiState(
        val postId: Long,
        val writer: String,
        val title: String,
        val artist: String,
        val cover: Uri?,
        val bitmap: Bitmap?,
        val message: String?,
        val genreType: GenreType?,
        val weatherType: WeatherType?,
        val withType: WithType?,
        val emotionType: EmotionType?,
        val distance: Double,
        val latitude: Double,
        val longitude: Double,
        val isLike: Boolean,
        val likeCnt: Int,
    ) {
        val distanceStr = distance.toString()
        val likeCntStr = likeCnt.toString()
    }
}
