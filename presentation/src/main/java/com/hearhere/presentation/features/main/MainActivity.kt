package com.hearhere.presentation.features.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.*
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.getResource
import com.hearhere.presentation.databinding.ActivityMainBinding
import com.hearhere.presentation.features.main.like.MarkerLikeActivity
import com.hearhere.presentation.features.main.profile.MarkerMyPostingActivity
import com.hearhere.presentation.features.post.PostActivity
import com.hearhere.presentation.util.createDrawableFromView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    OnMapsSdkInitializedCallback,
    OnMapReadyCallback {

    private val REQUEST_PERMISSION_CODE = 1
    private val DEFAULT_ZOOM_LEVEL = 16f
    private val CITY_HALL = LatLng(37.566667, 126.978427)
    private val DEFAULT_LOCATION = CITY_HALL
    private val MYLOCATION_TAG = "myLocation"
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val viewModel: MainViewModel by viewModels()
    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var markerDetailDialog: MarkerDetailBottomSheet
    private lateinit var markerCreateDialog: MarkerCreateDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this)
        binding.mapView.onCreate(savedInstanceState)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        initMap()
        if (hasPermission()) {
            initMap()
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        binding.likeBtn.setOnClickListener {
            MarkerLikeActivity.start(this)
        }
        binding.recomandBtn.setOnClickListener {
            RecomandActivity.start(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        mMap?.let {
            val location = viewModel.myLocation.value ?: getMyLocation()
            viewModel.requestPins(location.latitude, location.longitude)
            initMap()
            // TODO 속도개선
        }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)
    override fun observeViewModel() {
        viewModel.events.flowWithLifecycle(lifecycle).onEach(::handleEvent).launchIn(lifecycleScope)
        viewModel.pinStateList.observe { list ->
            if (list.isNotEmpty()) {
                mMap?.let {
                    createMarker(list)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        initMap()
    }

    private fun hasPermission(): Boolean {
        for (permission in PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    override fun onMapsSdkInitialized(renderer: MapsInitializer.Renderer) {
        when (renderer) {
            MapsInitializer.Renderer.LATEST -> Log.d(
                "MapsDemo",
                "The latest version of the renderer is used."
            )

            MapsInitializer.Renderer.LEGACY -> Log.d(
                "MapsDemo",
                "The legacy version of the renderer is used."
            )

            else -> {}
        }
    }

    private fun handleEvent(viewEvents: List<MainViewModel.PinEvent>) {
        viewEvents.firstOrNull()?.let { viewEvent ->
            mMap?.let {
                when (viewEvent) {
                    is MainViewModel.PinEvent.OnCompletedLoad -> {
//                        // Toast.makeText(this,"completed load",Toast.LENGTH_SHORT).show()
//                        mMap?.let {
//                            createMarker(viewEvent.pin) //                      }
                    }

                    is MainViewModel.PinEvent.OnChangeSelectedPin -> {
                    }

                    is MainViewModel.PinEvent.OnClickMyLocation -> {
                        setCameraToMyLocation(viewModel.myLocation.value)
                    }

                    is MainViewModel.PinEvent.OnClickList -> {
                        MarkerListActivity.start(this)
                    }

                    is MainViewModel.PinEvent.OnClickCreate -> {
                        showMarkerCreateDialog()
                    }

                    is MainViewModel.PinEvent.OnClickMyProfile -> {
                        MarkerMyPostingActivity.start(this)
                    }
                }
            }
            viewModel.consumeViewEvent(viewEvent)
        }
    }

    private fun setFocusMarker(marker: Marker, isFocused: Boolean) {
        if (mMap == null) return
        val pin = viewModel.getPinStateByMarker(marker) ?: return
        marker.apply {
            zIndex = if (isFocused) 1F else 0F
            setIcon(
                BitmapDescriptorFactory.fromBitmap(
                    this@MainActivity.createDrawableFromView(
                        (
                            pin.bitmap ?: BitmapFactory.decodeResource(
                                resources,
                                GenreType.valueOf(pin.pin.genreType).getResource()
                            )
                            ),
                        isFocused
                    )
                )
            )
        }
    }

    private fun createMarker(list: List<MainViewModel.PinState>) {
        val markers = ArrayList<Marker>()
        try {
            list.forEach {
                val markerOptions = MarkerOptions().apply {
                    position(LatLng(it.pin.latitude, it.pin.longitude))
                    icon(
                        BitmapDescriptorFactory.fromBitmap(
                            this@MainActivity.createDrawableFromView(
                                (
                                    it.bitmap ?: BitmapFactory.decodeResource(
                                        resources,
                                        GenreType.valueOf(it.pin.genreType).getResource()
                                    )
                                    ),
                                false
                            )
                        )
                    )
                }
                val marker = mMap!!.addMarker(markerOptions)
                marker?.tag = it.pin.postId
                markers.add(marker!!)
            }

            viewModel._markerList.postValue(markers)
        } catch (e: Error) {
            Log.e("bitmap error", e.toString())
        }
    }

//    private fun createMarkerOne(posting: MainViewModel.PinState) {
//        val markers = ArrayList<Marker>()
//        markers.addAll(viewModel.markerList.value ?: emptyList())
//
//        try {
//            val icon = GenreType.valueOf(posting.pin.genreType).getResource()
//            val markerOptions = MarkerOptions().apply {
//                position(LatLng(posting.pin.latitude, posting.pin.longitude))
//                icon(
//                    BitmapDescriptorFactory.fromBitmap(
//                        this@MainActivity.createDrawableFromView(
//                            (
//                                posting.bitmap ?: BitmapFactory.decodeResource(
//                                    resources,
//                                    GenreType.valueOf(posting.pin.genreType).getResource()
//                                )
//                                ).getCircledBitmap(),
//                            false
//                        )
//                    )
//                )
//            }
//            val marker = mMap!!.addMarker(markerOptions)
//            marker?.tag = posting.pin.postId
//            markers.add(marker!!)
//            viewModel._markerList.postValue(markers)
//        } catch (e: Error) {
//            Log.e("bitmap error", e.toString())
//        }
//    }

    private fun setMyLocation() {
        val location = getMyLocation()
        val option = MarkerOptions().apply {
            position(location)
            icon(
                BitmapDescriptorFactory.fromBitmap(
                    getDrawable(R.drawable.ic_mylocation)!!.toBitmap(
                        50,
                        50
                    )
                )
            )
        }
        viewModel.myLocationMarker.value?.let { it.remove() }

        val myLocationMarker = mMap!!.addMarker(option)
        myLocationMarker?.tag = MYLOCATION_TAG
        viewModel.myLocationMarker.postValue(myLocationMarker)

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM_LEVEL))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("hyom", "onMap Ready")
        mMap = googleMap
        setMyLocation()

        mMap?.setOnMarkerClickListener {
            runBlocking {
                viewModel.markerList.value?.forEach { marker ->
                    setFocusMarker(marker, false)
                }
            }
            if (it.tag != MYLOCATION_TAG) setFocusMarker(it, true)
            if (it.tag != MYLOCATION_TAG) showMarkerDialog(it.tag as Long)

            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(it.position, DEFAULT_ZOOM_LEVEL))
            return@setOnMarkerClickListener true
        }
    }

    private fun initMap() {
        var location: LatLng = DEFAULT_LOCATION
        location = getMyLocation()

        viewModel.requestPins(location.latitude, location.longitude)
        viewModel.setMyLocation(location)

        binding.mapView.getMapAsync {
            mMap = it

            it.uiSettings.isMyLocationButtonEnabled = false // 현재 위치로 이동 button을 비활성화
            if (hasPermission()) {
                try {
                    it.isMyLocationEnabled = true

                    it.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
                } catch (e: SecurityException) {
                } catch (e: Resources.NotFoundException) {
                }
            } else {
                // Toast.makeText(this, "접근 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            }

            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM_LEVEL))
            onMapReady(mMap!!)
        }
    }

    private fun setCameraToMyLocation(location: LatLng?) {
        mMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                location ?: getMyLocation(),
                DEFAULT_ZOOM_LEVEL
            )
        )
    }

    private fun showMarkerDialog(postId: Long) {
        if (::markerDetailDialog.isInitialized && markerDetailDialog.isAdded) {
            markerDetailDialog.dismiss()
        }

        markerDetailDialog = MarkerDetailBottomSheet.newInstance(postId).also { dialog ->
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    fun colorPin(id: Long) {
        val marker = viewModel.getMarkerById(id)
        // Toast.makeText(this,"resume dialog"+id.toString(),Toast.LENGTH_SHORT).show()
        marker?.let {
            Log.d("resum dialog marker", (marker.tag as Long).toString())
            setFocusMarker(marker, true)
        }
    }

    private fun showMarkerCreateDialog() {
        if (::markerCreateDialog.isInitialized && markerCreateDialog.isAdded) {
            markerCreateDialog.dismiss()
        }

        markerCreateDialog = MarkerCreateDialog.newInstance(markerCreateListener).also { dialog ->
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    private val markerCreateListener = object : MarkerCreateDialog.OnClickDialog {
        override fun onClickPositive() {
            // Toast.makeText(this@MainActivity, "yes", Toast.LENGTH_SHORT).show()
            Intent(this@MainActivity, PostActivity::class.java).also {
                startActivity(it)
            }
        }

        override fun onClickNegative() {
            Toast.makeText(this@MainActivity, "no", Toast.LENGTH_SHORT).show()
            markerCreateDialog?.dismiss()
        }
    }

    private fun getMyLocation(): LatLng {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val lastKnownLocation =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            lastKnownLocation?.apply { return LatLng(latitude, longitude) }
        } catch (e: SecurityException) {
        } catch (e: IllegalArgumentException) {
        }
        return DEFAULT_LOCATION
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            ContextCompat.startActivity(context, intent, null)
        }
    }
}
