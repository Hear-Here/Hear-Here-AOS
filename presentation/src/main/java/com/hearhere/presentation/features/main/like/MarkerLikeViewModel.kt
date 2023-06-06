package com.hearhere.presentation.features.main.like

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.usecase.GetPostUseCase
import com.hearhere.domain.usecaseImpl.GetPostUseCaseImpl
import com.hearhere.domain.usecaseImpl.PatchPostUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.features.main.MainViewModel
import com.hearhere.presentation.features.main.adapter.MarkerLikeItemBinder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkerLikeViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCaseImpl,
    private val patchPostUseCase: PatchPostUseCaseImpl
) : BaseViewModel() {
    private val _events = MutableStateFlow<List<MarkerLikeEvent>>(emptyList())
    val events get() = _events.asStateFlow()

    private val _uiState = MutableLiveData<List<MarkerLikeItemState>>(emptyList())
    val uiState get() = _uiState

    private val _binder = MutableLiveData<List<MarkerLikeItemBinder>>(emptyList())
    val binder get() = _binder

    init {
        //call API
        getMarkerList()
    }

   fun getMarkerList() {
        viewModelScope.launch {
            val res = getPostUseCase.getLikePostList()

            when(res){
                is ApiResponse.Success->{
                    val temp = arrayListOf<MarkerLikeItemState>()
                    val binders = arrayListOf<MarkerLikeItemBinder>()
                    res.data?.forEach {
                        val post = MarkerLikeItemState(
                            postId = it.postId,
                            artist = it.artist,
                            title = it.title,
                            coverPath = it.coverPath,
                            distance = it.distance
                        )
                        val binder = MarkerLikeItemBinder(
                            ::onClickDetail,
                            ::onClickItemMenu
                        )
                        binder.setMarker(post)

                        temp.add(post)
                        binders.add(binder)
                    }.also {
                        _uiState.postValue(temp)
                        _binder.postValue(binders)
                    }

                }
                else->{
                    Log.d("hyom",res.message.toString())
                }
            }
        }

    }

    private fun onClickDetail(postId: Long) {
        //delete api 위치
        addEvent(MarkerLikeEvent.OnClickDetail(postId))
    }


    private fun onClickItemMenu(postId: Long, title: String) {
        addEvent(MarkerLikeEvent.ShowDialog(postId, title))
    }

    fun onClickItemDelete(postId: Long) {
        //delete api 위치
        viewModelScope.launch {
            patchPostUseCase.disLikePost(postId)
        }
        addEvent(MarkerLikeEvent.DismissDialog)
    }

    fun onClickItemCopy(title: String) {
        //copy logic
        addEvent(MarkerLikeEvent.DismissDialog)
    }


    private fun addEvent(event: MarkerLikeEvent) {
        _events.update { it + event }
    }

    fun consumeViewEvent(event: MarkerLikeEvent) {
        _events.update { it - event }
    }


    data class MarkerLikeItemState(
        val postId: Long,
        val title: String,
        val artist: String,
        val coverPath: String? = "",
        val distance: Double,
    )

    sealed class MarkerLikeEvent() {
        data class OnClickDetail(val postId: Long) : MarkerLikeEvent()
        data class ShowDialog(val postId: Long, val title: String) : MarkerLikeEvent()
        object DismissDialog : MarkerLikeEvent()

        data class CopyTitle(val title : String) : MarkerLikeEvent()
    }
}