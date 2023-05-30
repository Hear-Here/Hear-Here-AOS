package com.hearhere.presentation.features.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Pin
import com.hearhere.domain.usecase.GetPostUseCase
import com.hearhere.domain.usecaseImpl.GetPostUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   private val getPostUseCase: GetPostUseCaseImpl
) : BaseViewModel() {

    private val _pinStateList = MutableLiveData<List<PinState>>(emptyList())
    val pinStateList: LiveData<List<PinState>>
        get() = _pinStateList

    private val _selectedPin = MutableLiveData<PinState>(null)
    val selectedPin: LiveData<PinState>
        get() = _selectedPin

    val markerList = MutableLiveData<List<Marker>>(emptyList())

    var myLocation = MutableLiveData<LatLng>(null)

    var myLocationMarker = MutableLiveData<Marker>(null)


    private val _events = MutableStateFlow<List<PinEvent>>(emptyList())
    val events = _events.asStateFlow()

    fun requestPins() {
        _loading.postValue(true)
        val tempList = arrayListOf<PinState>()
        val repsonse: List<Pin> = listOf<Pin>(
            Pin(
                1, "https://artmug.kr/image/goods_img1/2/24528.jpg?ver=1668349176", 37.495081,
                126.957395
            ),
            Pin(
                2, "https://i1.sndcdn.com/artworks-000660272461-rmfvxq-t500x500.jpg", 37.493081,
                126.957395
            ),
            Pin(
                6,
                "https://thumb.pann.com/tc_480/http://fimg5.pann.com/new/download.jsp?FileID=53302512",
                37.492081,
                126.958395
            ),
            Pin(
                3,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTF4pThwkGQrSpSRNQ7m3ZT25JtGkGE2dvqxPId0IO1jSnShCPfspcBhns6vPRupwrU14&usqp=CAU",
                37.496081,
                126.959395
            ),
            Pin(
                40,
                "https://i1.sndcdn.com/artworks-CDyMPstbky5qw7oe-NfF8Pg-t240x240.jpg",
                37.496081,
                126.967395
            ),
            Pin(
                5,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSk58wYEVvPBpoqZ-RBeb5XQARvxEwaMenAat6x-Qo86LxecYnsABrf5CxPEtS0ZmQvD_s&usqp=CAU",
                37.497081,
                126.957695
            ),
            Pin(
                11, "", 37.497081,
                126.957395
            ),
            Pin(
                13, "", 37.497081,
                126.957595
            ),
        )

        /**
        viewModelScope.launch {
            if(myLocation.value!=null){
                getPostUseCase.getPostList( myLocation.value!!.latitude,
                    myLocation.value!!.longitude).also {
                    when(it){
                        is ApiResponse.Success ->{}
                        is ApiResponse.Error->{}
                    }
                }
            }
        }
        **/

        repsonse.forEach {
            tempList.add(PinState(it, null))
        }
        fetchPins(tempList)
    }

    private fun fetchPins(list: List<PinState>) {
        if (list.isEmpty()) return
        val newPinList = arrayListOf<PinState>()

        list.forEach { item ->
            if (item.pin.imageUrl.isNullOrEmpty()) newPinList.add(item)
            else newPinList.add(item.copy(bitmap = loadUrlToBitmap(item.pin.imageUrl!!)))
            Log.e("item", item.toString())
        }

        _pinStateList.postValue(newPinList)
        _loading.postValue(false)

        addEvent(PinEvent.OnCompletedLoad)
    }

    fun setSelectedPin(postId: Long?) {
        pinStateList.value?.firstOrNull() { it.pin.postId == postId }?.also {
            _selectedPin.postValue(it)
            addEvent(PinEvent.OnChangeSelectedPin)
        }
    }

    fun getPinStateByMarker(marker: Marker): PinState? {
        val postId = marker.tag ?: return null
        return pinStateList?.value?.firstOrNull() { it.pin.postId == postId }
    }

    fun getMarkerByPinState(pinState: PinState): Marker? {
        return markerList.value?.firstOrNull() { it.tag == pinState.pin.postId }
    }

    fun setMyLocationMarker(location: LatLng) {

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
        object OnChangeSelectedPin : PinEvent()

        object OnClickMyLocation : PinEvent()

        object OnClickList : PinEvent()

        object OnClickCreate : PinEvent()

        object OnClickMyProfile : PinEvent()
    }
}