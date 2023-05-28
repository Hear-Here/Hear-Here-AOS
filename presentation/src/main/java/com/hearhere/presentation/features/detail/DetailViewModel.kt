package com.hearhere.presentation.features.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
class DetailViewModel @Inject constructor(   private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val POST_ID = "postId"

    private val _uiState = MutableLiveData<PostingDetailUiState?>(null)
    val uiState: LiveData<PostingDetailUiState?> get() = _uiState

    var postId = 0
        get() = savedStateHandle.get<Int>(POST_ID) ?: -1

    private var toggleJob: Job? = null

    init {
        getMarkerDetail()
    }

    private fun getMarkerDetail() {
        _loading.postValue(true)
        //TODO
        _uiState.postValue(
            PostingDetailUiState(
                postId = postId,
                writer = "영종킴" + postId.toString(),
                title = "노래노래",
                artist = "hihihihi",
                cover = Uri.parse(""),
                bitmap = loadUrlToBitmap(""),
                message = "hefheihoehaoehfoi",
                genreType = GenreType.HIPHOP,
                weatherType = WeatherType.RAINY,
                withType = WithType.FAMILY,
                emotionType = EmotionType.HEART,
                distance = 50.0,
                isLike = false,
                likeCnt = 180,
                latitude = 37.566667,
                longitude =126.978427 ,
            )
        )
        _loading.postValue(false)
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

        //디바운스 처리
        toggleJob?.cancel()
        toggleJob = viewModelScope.launch {
            delay(2000)
            //API
            Log.d("bottom toggle", uiState.value?.isLike.toString())

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
        val postId: Int,
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
        val latitude : Double,
        val longitude : Double,
        val isLike: Boolean,
        val likeCnt: Int,
    ) {
        val distanceStr = distance.toString()
        val likeCntStr = likeCnt.toString()
    }
}