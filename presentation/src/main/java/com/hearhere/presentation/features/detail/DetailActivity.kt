package com.hearhere.presentation.features.detail

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityDetailBinding
import com.hearhere.presentation.features.main.like.MarkerLikeActivity
import com.hearhere.presentation.util.createDrawableFromView
import com.hearhere.presentation.util.getCircledBitmap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity  : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail),
    OnMapsSdkInitializedCallback, OnMapReadyCallback {
    private val viewModel : DetailViewModel by viewModels()
    private var mMap: GoogleMap? = null

    private val DEFAULT_ZOOM_LEVEL = 16f
    private val CITY_HALL = LatLng(37.566667, 126.978427)
    private val DEFAULT_LOCATION = CITY_HALL

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding.viewModel =  viewModel
    }

    override fun onRestart() {
        viewModel.getMarkerDetail()
        super.onRestart()
    }

    override fun onResume() {
        Log.d("hyomk","resume")
        viewModel.getMarkerDetail()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this)
        binding.mapView.onCreate(savedInstanceState)

    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.uiState.observe {
            if(it!=null){
                initMap()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun initMap() {
        binding.mapView.getMapAsync {
            mMap = it
            val post = viewModel.uiState.value!!
            val markerOptions = MarkerOptions().apply {
                position(LatLng(post.latitude, post.longitude))
                icon(
                    BitmapDescriptorFactory.fromBitmap(
                        createDrawableFromView(
                            (post.bitmap ?: BitmapFactory.decodeResource(
                                resources,
                                com.hearhere.presentation.common.R.drawable.headphones
                            )).getCircledBitmap(),
                            false
                        )
                    )
                )

            }
            val marker = mMap!!.addMarker(markerOptions)
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(post.latitude,post.longitude), DEFAULT_ZOOM_LEVEL))
            onMapReady(mMap!!)
        }
    }

    override fun onMapsSdkInitialized(renderer: MapsInitializer.Renderer) {
        when (renderer) {
            MapsInitializer.Renderer.LATEST -> Log.d(
                "MapsDemo", "The latest version of the renderer is used."
            )
            MapsInitializer.Renderer.LEGACY -> Log.d(
                "MapsDemo", "The legacy version of the renderer is used."
            )
            else -> {}
        }
    }

    companion object{
        fun start(context : Context, postId:Long){
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("POST_ID",postId)
            }
            ContextCompat.startActivity(context, intent, null)
        }
    }
}