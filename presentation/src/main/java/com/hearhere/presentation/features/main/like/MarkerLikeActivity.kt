package com.hearhere.presentation.features.main.like

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.util.MarginItemDecoration
import com.hearhere.presentation.databinding.ActivityMarkerLikeBinding
import com.hearhere.presentation.features.detail.DetailActivity
import com.hearhere.presentation.util.ConvertDPtoPX
import com.hearhere.presentation.util.CopyOnClipboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MarkerLikeActivity : BaseActivity<ActivityMarkerLikeBinding>(R.layout.activity_marker_like) {
    private lateinit var adapter: BaseAdapter
    private val viewModel: MarkerLikeViewModel by viewModels()

    private lateinit var dialog: MarkerLikeDialog

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        adapter = BaseAdapter.build()
        binding.markerListRv.let {
            it.adapter = adapter
            it.addItemDecoration(MarginItemDecoration(ConvertDPtoPX(this, 8)))
        }
        binding.backBtn.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMarkerList()
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.binder.observe {
            adapter.submitList(it)
            Log.d("binder", it.toString())
        }

        viewModel.uiState.observe {
            Log.d("state", it.toString())
        }

        viewModel.events.flowWithLifecycle(lifecycle).onEach(::handleEvent).launchIn(lifecycleScope)
    }

    private fun handleEvent(viewEvents: List<MarkerLikeViewModel.MarkerLikeEvent>) {
        viewEvents.firstOrNull()?.let { event ->
            when (event) {
                is MarkerLikeViewModel.MarkerLikeEvent.ShowDialog -> {
                    showDialog(event.postId, event.title)
                }
                is MarkerLikeViewModel.MarkerLikeEvent.DismissDialog -> {
                    dismissDialog()
                }
                is MarkerLikeViewModel.MarkerLikeEvent.OnClickDetail -> {
                    DetailActivity.start(this@MarkerLikeActivity, event.postId.toLong())
                }
                is MarkerLikeViewModel.MarkerLikeEvent.CopyTitle -> {
                    onCopy(event.title)
                    dismissDialog()
                }
                is MarkerLikeViewModel.MarkerLikeEvent.Delete -> {
                    viewModel.getMarkerList()
                    dismissDialog()
                }
            }
            viewModel.consumeViewEvent(event)
        }
    }

    private fun onCopy(text: String) {
        this.CopyOnClipboard(text)
    }

    private fun showDialog(postId: Long, title: String) {
        if (::dialog.isInitialized && dialog.isAdded) {
            dialog.dismiss()
        }

        dialog = MarkerLikeDialog.newInstance(postId, title).also { dialog ->
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    private fun dismissDialog() {
        if (::dialog.isInitialized && dialog.isAdded) {
            dialog.dismiss()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MarkerLikeActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}
