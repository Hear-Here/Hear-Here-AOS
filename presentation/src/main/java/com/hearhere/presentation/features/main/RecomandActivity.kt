package com.hearhere.presentation.features.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.common.util.MarginItemDecoration
import com.hearhere.presentation.databinding.ActivityRecomandBinding
import com.hearhere.presentation.features.detail.DetailActivity
import com.hearhere.presentation.util.ConvertDPtoPX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecomandActivity : BaseActivity<ActivityRecomandBinding>(R.layout.activity_recomand) {
    private val viewModel: MarkerListViewModel by viewModels()
    private lateinit var adapter: BaseAdapter

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this

        adapter = BaseAdapter.build()
        binding.markerListRv.let {
            it.adapter = adapter
            it.addItemDecoration(MarginItemDecoration(ConvertDPtoPX(this, 8)))
        }
        binding.backBtn.setOnClickListener { finish() }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.binder.observe {
            adapter.submitList(it.shuffled())
        }

        viewModel.uiState.observe {
        }

        viewModel.navigateToDetails.observe {
            if (it !== null) DetailActivity.start(this, it)
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RecomandActivity::class.java)
            startActivity(context, intent, null)
        }
    }
}
