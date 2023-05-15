package com.hearhere.presentation.features.main.like

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.util.MarginItemDecoration
import com.hearhere.presentation.databinding.ActivityMarkerLikeBinding
import com.hearhere.presentation.util.ConvertDPtoPX

class MarkerLikeActivity  : BaseActivity<ActivityMarkerLikeBinding>(R.layout.activity_marker_like){
    private lateinit var adapter : BaseAdapter
    private val viewModel : MarkerLikeViewModel by viewModels()

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        adapter = BaseAdapter.build()
        binding.markerListRv.let{
            it.adapter=adapter
            it.addItemDecoration(MarginItemDecoration(ConvertDPtoPX(this,8)))
        }
        binding.backBtn.setOnClickListener { finish() }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.binder.observe {
            adapter.submitList(it)
            Log.d("binder",it.toString())
        }

        viewModel.uiState.observe {
            Log.d("state",it.toString())
        }
    }

    companion object{
        fun start(context : Context){
            val intent = Intent(context, MarkerLikeActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}