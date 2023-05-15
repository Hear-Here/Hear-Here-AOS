package com.hearhere.presentation.features.main

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
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MarkerDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(){

    val POST_ID = "postId"

    private val _uiState = MutableLiveData<MarkerDetailUiState>()
    val uiState : LiveData<MarkerDetailUiState> get() = _uiState

    var postId = 0
        get()  = savedStateHandle.get<Int>(POST_ID)?:-1

    private var toggleJob : Job?=null

    init {
        getMarkerDetail()
    }

    private fun getMarkerDetail(){
        _loading.postValue(true)
        //TODO
        _uiState.postValue(
            MarkerDetailUiState(
                postId = postId,
                writer = "영종킴"+postId.toString(),
                title = "노래노래",
                artist = "hihihihi",
                cover = Uri.parse(""),
                message = "hefheihoehaoehfoi",
                genreType = GenreType.HIPHOP,
                weatherType = WeatherType.RAINY,
                withType = WithType.FAMILY,
                emotionType = EmotionType.HEART,
                distance = 50.0,
                isLike = false
            )
        )

        _loading.postValue(false)
    }

    fun onClickLikeToggle(){
        _uiState.postValue(uiState.value?.copy(isLike = !uiState.value!!.isLike))
         //디바운스 처리
         toggleJob?.cancel()
         toggleJob = viewModelScope.launch {
            delay(2000)
            //API
             Log.d("bottom toggle",uiState.value?.isLike.toString())
         }
    }

    data class MarkerDetailUiState(
        val postId : Int,
        val writer : String,
        val title : String,
        val artist : String,
        val cover : Uri?,
        val message : String?,
        val genreType: GenreType?,
        val weatherType: WeatherType?,
        val withType: WithType?,
        val emotionType: EmotionType?,
        val distance : Double,
        val isLike : Boolean,
    ){
        val distanceStr = distance.toString()
    }

}