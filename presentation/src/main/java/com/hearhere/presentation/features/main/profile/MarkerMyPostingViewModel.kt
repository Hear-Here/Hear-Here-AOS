package com.hearhere.presentation.features.main.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.usecaseImpl.GetPostUseCaseImpl
import com.hearhere.domain.usecaseImpl.PatchPostUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.features.main.adapter.MarkerLikeItemBinder
import com.hearhere.presentation.features.main.adapter.MarkerMyListItemBinder
import com.hearhere.presentation.features.main.like.MarkerLikeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkerMyPostingViewModel @Inject constructor(
    private val patchUseCase: PatchPostUseCaseImpl,
    private val getPostUseCase: GetPostUseCaseImpl
) : BaseViewModel() {
    private val _events = MutableStateFlow<List<MarkerMyPostingEvent>>(emptyList())
    val events get() = _events.asStateFlow()

    private val _uiState = MutableLiveData<List<MarkerMyItemState>>(emptyList())
    val uiState get() = _uiState

    private val _binder = MutableLiveData<List<MarkerMyListItemBinder>>(emptyList())
    val binder get() = _binder

    init {
        //call API
        getMarkerList()
    }

    fun getMarkerList() {
        viewModelScope.launch {
            val res = getPostUseCase.getMyPostList()
            when(res){
                is ApiResponse.Success->{
                    val temp = arrayListOf<MarkerMyItemState>()
                    val binders = arrayListOf<MarkerMyListItemBinder>()
                    res.data?.forEach {
                        val post = MarkerMyItemState(
                            postId = it.postId,
                            artist = it.artist,
                            title = it.title,
                            coverPath = it.coverPath,
                            distance = it.distance,
                            writer = it.writer
                        )
                        temp.add(post)
                        val binder = MarkerMyListItemBinder(
                            ::onClickDetail,
                            ::onClickItemMenu
                        )
                        binder.setMarker(post)
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


    private fun onClickDetail(postId:Long) {
        //delete api 위치
        addEvent(MarkerMyPostingEvent.OnClickDetail(postId))
    }


    private fun onClickItemMenu(postId: Long, title: String) {
        addEvent(MarkerMyPostingEvent.ShowDialog(postId, title))
    }

    fun onClickItemDelete(postId: Long) {
        //delete api 위치
        viewModelScope.launch {
            patchUseCase.deletePost(postId).also {
                getMarkerList()
            }
        }
        addEvent(MarkerMyPostingEvent.DismissDialog)
    }

    fun onClickItemCopy(title: String) {
        //copy logic
        addEvent(MarkerMyPostingEvent.CopyTitle(title))
    }


    private fun addEvent(event: MarkerMyPostingEvent) {
        _events.update { it + event }
    }

    fun consumeViewEvent(event: MarkerMyPostingEvent) {
        _events.update { it - event }
    }


    data class MarkerMyItemState(
        val postId: Long,
        val title: String,
        val artist: String,
        val coverPath: String? = "",
        val distance: Double,
        val writer : String ?=""
    )

    sealed class MarkerMyPostingEvent() {
        data class OnClickDetail(val postId: Long) : MarkerMyPostingEvent()
        data class ShowDialog(val postId: Long, val title: String) : MarkerMyPostingEvent()
        object DismissDialog : MarkerMyPostingEvent()

        data class CopyTitle(val title: String) : MarkerMyPostingEvent()
    }
}
