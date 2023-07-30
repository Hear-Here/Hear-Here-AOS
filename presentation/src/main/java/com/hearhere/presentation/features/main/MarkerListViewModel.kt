package com.hearhere.presentation.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.PostQuery
import com.hearhere.domain.usecaseImpl.GetPostUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.features.main.adapter.MarkerListItemBinder
import com.hearhere.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkerListViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCaseImpl
) : BaseViewModel() {

    private val _uiState = MutableLiveData<List<MarkerListItemState>>(emptyList())
    val uiState get() = _uiState

    private val _binder = MutableLiveData<List<MarkerListItemBinder>>(emptyList())
    val binder get() = _binder

    private val _navigateToDetails = SingleLiveEvent<Long?>()

    val navigateToDetails: LiveData<Long?>
        get() = _navigateToDetails

    init {
        // call API
        requestPins()
    }

    fun requestPins() {
        viewModelScope.launch {
            _loading.postValue(true)
            val location = getPostUseCase.myLocation ?: getPostUseCase.getLocation()
            if (location != null) {
                getPostUseCase.getPostList(
                    PostQuery(
                        lat = location.lat,
                        lng = location.lng
                    )
                ).also {
                    when (it) {
                        is ApiResponse.Success -> {
                            val tempList = arrayListOf<MarkerListItemState>()
                            it.data?.forEach {
                                tempList.add(
                                    MarkerListItemState(
                                        postId = it.postId,
                                        coverPath = it.imageUrl,
                                        artist = it.artist ?: "",
                                        title = it.title ?: "",
                                        distance = it.distance ?: 0.0,
                                        writer = it.writer ?: ""
                                    )
                                )
                            }
                            _uiState.postValue(tempList)
                            val binders = arrayListOf<MarkerListItemBinder>()
                            tempList.forEach {
                                val binder = MarkerListItemBinder(::onClickItem)
                                binder.setMarker(it)
                                binders.add(binder)
                            }.also {
                                binder.postValue(binders)
                            }
                            _loading.postValue(false)
                        }
                        is ApiResponse.Error -> {}
                    }
                }
            }
        }
    }

    fun onClickItem(postId: Long) {
        _navigateToDetails.value = postId
        _navigateToDetails.call()
    }

    data class MarkerListItemState(
        val postId: Long,
        val title: String,
        val artist: String,
        val coverPath: String? = "",
        val distance: Double,
        val writer: String
    )
}
