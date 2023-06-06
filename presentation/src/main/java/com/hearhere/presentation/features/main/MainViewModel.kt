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
import com.hearhere.domain.usecaseImpl.PatchPostUseCaseImpl
import com.hearhere.domain.usecaseImpl.PatchUserInfoUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
   private val getPostUseCase: GetPostUseCaseImpl,
   private val patchUserInfoUseCase: PatchUserInfoUseCaseImpl
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

    init {
        viewModelScope.launch {
            val location = getPostUseCase.myLocation ?: getPostUseCase.getLocation()
            if(location!=null) myLocation.postValue(LatLng(location.lat,location.lng))
            Log.d("hyomk - location",location.toString())
        }

    }

   fun requestPins(lat : Double, lng : Double) {
        viewModelScope.launch {
            _loading.postValue(true)
            getPostUseCase.getPostList( lat,lng ).also {
                when(it){
                    is ApiResponse.Success ->{
                        val tempList = arrayListOf<PinState>()
                        it.data?.forEach {
                            tempList.add(PinState(it, null))
                        }
                        fetchPins(tempList)
                    }
                    is ApiResponse.Error->{}
                }
            }
        }

    }

    private fun fetchPins(list: List<PinState>) {
        CoroutineScope(Dispatchers.IO).launch {
            if (list.isEmpty()) return@launch
            val newPinList = arrayListOf<PinState>()
            list.forEach { item ->
                if (item.pin.imageUrl.isNullOrEmpty()) newPinList.add(item)
                else newPinList.add(item.copy(bitmap = loadUrlToBitmap(item.pin.imageUrl!!)))
            }
            _pinStateList.postValue(newPinList)
            _loading.postValue(false)
            addEvent(PinEvent.OnCompletedLoad)
        }
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

    fun setMyLocation(location: LatLng) {
        myLocation.postValue(location)
        viewModelScope.launch {
            patchUserInfoUseCase.updateLocation(location.latitude,location.longitude)
        }
    }


    private fun loadUrlToBitmap(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        val connection: HttpURLConnection?

        try {
            val url = URL(url)
            connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET" // request 방식 설정
            connection.connectTimeout = 10000 // 10초의 타임아웃
            connection.doOutput = true // OutPutStream으로 데이터를 넘겨주겠다고 설정
            connection.doInput = true // InputStream으로 데이터를 읽겠다는 설정
            connection.useCaches = true // 캐싱 여부
            connection.connect()

            val resCode = connection.responseCode // 연결 상태 확인
            if (resCode == HttpURLConnection.HTTP_OK) { // 200일때 bitmap으로 변경
                val input = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input) // BitmapFactory의 메소드를 통해 InputStream으로부터 Bitmap을 만들어 준다.
                connection.disconnect()
            }
        } catch (e: IOException) {
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