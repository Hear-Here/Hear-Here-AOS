package com.hearhere.presentation.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.hearhere.domain.model.Posting
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityPostFinishBinding
import com.hearhere.presentation.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFinishActivity : BaseActivity<ActivityPostFinishBinding>(R.layout.activity_post_finish) {
    private val viewModel: PostFinishViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val posting: Posting = intent.getSerializableExtra("music") as Posting
        Log.d("옥채연", posting.toString())

        viewModel.getDetail(posting)
        observeViewModel()
        binding.viewModel = viewModel

        binding.postFinishBtn.setOnClickListener {
            viewModel.requestPost(posting)
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
    }
}
