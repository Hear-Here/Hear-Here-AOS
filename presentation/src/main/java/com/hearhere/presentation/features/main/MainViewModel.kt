package com.hearhere.presentation.features.main

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Pin
import com.hearhere.domain.model.PostQuery
import com.hearhere.domain.usecaseImpl.GetPostUseCaseImpl
import com.hearhere.domain.usecaseImpl.PatchUserInfoUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCaseImpl,
    private val patchUserInfoUseCase: PatchUserInfoUseCaseImpl
) : BaseViewModel() {

    private val _pinStateList = MutableLiveData<List<PinState>>(emptyList())
    val pinStateList: LiveData<List<PinState>>
        get() = _pinStateList

    private val _selectedPin = MutableLiveData<PinState>(null)

    var selectedPin: PinState? = null

    val _markerList = MutableLiveData<List<Marker>>(emptyList())
    val markerList get() = _markerList

    val _myLocation = MutableLiveData<LatLng>(null)

    val myLocation get() = _myLocation

    var myLocationMarker = MutableLiveData<Marker>(null)

    private val _events = MutableStateFlow<List<PinEvent>>(emptyList())
    val events = _events.asStateFlow()

    var isFetching: Job? = null

    init {
        viewModelScope.launch {
            val location = getPostUseCase.myLocation ?: getPostUseCase.getLocation()
            if (location != null) myLocation.postValue(LatLng(location.lat, location.lng))
        }
    }

    fun requireFetchPins(list: List<Pin>) {
        _loading.postValue(true)
        viewModelScope.launch {
            val tempList = arrayListOf<PinState>()
            list.reversed()?.forEach {
                tempList.add(PinState(it, null))
            }
            fetchPins(tempList)
            delay(3000)
        }
    }

    private fun fetchPins(list: List<PinState>) {
        viewModelScope.launch {
            val newPinList = arrayListOf<PinState>()
            list.forEach { item ->
                val pin = if (item.pin.imageUrl.isNullOrEmpty()) {
                    item
                } else {
                    item
                }

                newPinList.add(pin)
            }
            _pinStateList.postValue(newPinList)
            addEvent(PinEvent.OnCompletedLoad)
        }
    }

    fun setSelectedPin(postId: Long?) {
        pinStateList.value?.filter { it.pin.postId == postId }
            .also {
                if (it != null) {
                    if(it.isEmpty())return@also
                }
                it?.first()?.let { pin ->
                    _selectedPin.postValue(pin)
                    selectedPin = pin
                    val marker = getMarkerByPinState(pin)
                    addEvent(PinEvent.OnChangeSelectedPin(marker ?: return))
                }
            }
    }

    fun getPinStateByMarker(marker: Marker): PinState? {
        val postId = marker.tag ?: return null
        return pinStateList?.value?.firstOrNull() { it.pin.postId == postId }
    }

    fun getMarkerByPinState(pinState: PinState): Marker? {
        val marker = markerList.value?.firstOrNull() { it.tag as Long == pinState.pin.postId }
        Log.d("select marker by pin ", marker.toString())
        return marker
    }

    fun getMarkerById(id: Long): Marker? {
        val marker = markerList.value?.firstOrNull() { it.tag as Long == id }
        Log.d("find marker", marker?.tag.toString())
        return marker
    }

    fun setMyLocation(location: LatLng) {
        viewModelScope.launch {
            _myLocation.postValue(location)
            patchUserInfoUseCase.updateLocation(location.latitude, location.longitude)
        }
    }

    fun onClickMyLocation() {
        addEvent(PinEvent.OnClickMyLocation)
    }

    fun onClickList() {
        addEvent(PinEvent.OnClickList)
    }

    fun onClickCreate() {
        addEvent(PinEvent.OnClickCreate)
    }

    fun onClickMyProfile() {
        addEvent(PinEvent.OnClickMyProfile)
    }

    private fun addEvent(event: PinEvent) {
        _events.update { it + event }
    }

    fun consumeViewEvent(event: PinEvent) {
        _events.update { it - event }
    }

    data class PinState(val pin: Pin, var bitmap: Bitmap?)
    sealed class PinEvent {
        object OnCompletedLoad : PinEvent()
        data class OnChangeSelectedPin(val marker: Marker) : PinEvent()

        object OnClickMyLocation : PinEvent()

        object OnClickList : PinEvent()

        object OnClickCreate : PinEvent()

        object OnClickMyProfile : PinEvent()
    }
}
