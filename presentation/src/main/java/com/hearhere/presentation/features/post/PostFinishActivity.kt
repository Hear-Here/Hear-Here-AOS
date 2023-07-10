package com.hearhere.presentation.features.post

import android.os.Bundle
import androidx.activity.viewModels
import com.hearhere.domain.model.Posting
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityPostFinishBinding
import com.hearhere.presentation.features.main.MainActivity
import com.hearhere.presentation.util.intentSerializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFinishActivity : BaseActivity<ActivityPostFinishBinding>(R.layout.activity_post_finish) {
    private val viewModel: PostFinishViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val posting = intent.intentSerializable("music", Posting::class.java)

        if (posting == null) {
            finish()
        }

        viewModel.getDetail(posting!!)
        observeViewModel()
        binding.viewModel = viewModel

        binding.postFinishBtn.setOnClickListener {
            viewModel.requestPost(posting)
            MainActivity.start(this)
        }
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
    }
}
