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
import com.hearhere.domain.usecaseImpl.GetPostUseCaseImpl
import com.hearhere.domain.usecaseImpl.PatchUserInfoUseCaseImpl
import com.hearhere.presentation.base.BaseViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import javax.inject.Inject
import kotlin.concurrent.thread

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
            Log.d("hyomk - location", location.toString())
        }

        viewModelScope.launch {
            delay(4000)
            _loading.postValue(false)
        }
    }

    fun requestPins(lat: Double, lng: Double) {
        // if(isFetching != null) isFetching?.cancel()
        viewModelScope.launch {
            _loading.postValue(true)
            delay(3000)
            _loading.postValue(false)
        }
        isFetching = viewModelScope.launch {
            // _loading.postValue(true)
            getPostUseCase.getPostList(lat, lng).also {
                when (it) {
                    is ApiResponse.Success -> {
                        val tempList = arrayListOf<PinState>()
                        it.data?.reversed()?.forEach {
                            tempList.add(PinState(it, null))
                        }
                        fetchPins(tempList)
                    }
                    is ApiResponse.Error -> {}
                }
            }
        }
    }

    private fun fetchPins(list: List<PinState>) {

        CoroutineScope(Dispatchers.IO).launch {
            if (list.isEmpty()) return@launch
            val newPinList = arrayListOf<PinState>()
            list.forEach { item ->
                Log.d("hyom item", item.toString())

                val pin = if (item.pin.imageUrl.isNullOrEmpty()) item
                else item.copy(bitmap = loadBitmapFromUrl(item.pin.imageUrl!!))

                newPinList.add(pin)
                Log.d("hyom item-after", pin.toString())
                _pinStateList.postValue(newPinList)

                Log.d("hyom-list", list.toString())
                addEvent(PinEvent.OnCompletedLoad(pin))
            }
        }
    }

    fun setSelectedPin(postId: Long?) {
        pinStateList.value?.filter { it.pin.postId == postId }
            .also {
                Log.d("pin state", it.toString())
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

    private fun loadUrlToBitmap(url: String): Bitmap? {
        thread {
        }

        return null
    }

    fun loadBitmapFromUrl(url: String): Bitmap? = runBlocking {
        var bitmap: Bitmap? = null
        var connection: HttpURLConnection? = null

        try {
            bitmap = Picasso.get().load(url).get()
//            val urlConnection = URL(url)
//            connection = urlConnection.openConnection() as HttpURLConnection
//
//            connection.requestMethod = "GET" // request 방식 설정
//            connection.connectTimeout = 10000 // 10초의 타임아웃
//            connection.doOutput = true // OutPutStream으로 데이터를 넘겨주겠다고 설정
//            connection.doInput = true // InputStream으로 데이터를 읽겠다는 설정
//            connection.useCaches = true // 캐싱 여부
//            connection.connect()
//
//            val resCode = connection.responseCode // 연결 상태 확인
//            if (resCode == HttpURLConnection.HTTP_OK) { // 200일때 bitmap으로 변경
//                val input = connection.inputStream
//                bitmap = BitmapFactory.decodeStream(input) // BitmapFactory의 메소드를 통해 InputStream으로부터 Bitmap을 만들어 준다.
//                connection.disconnect()
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }

        bitmap
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
        data class OnCompletedLoad(val pin: PinState) : PinEvent()
        data class OnChangeSelectedPin(val marker: Marker) : PinEvent()

        object OnClickMyLocation : PinEvent()

        object OnClickList : PinEvent()

        object OnClickCreate : PinEvent()

        object OnClickMyProfile : PinEvent()
    }
}
