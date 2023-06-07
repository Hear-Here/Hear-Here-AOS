package com.hearhere.presentation.post

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.hearhere.domain.model.Posting
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityPostFinishBinding
import com.hearhere.presentation.features.main.MainActivity
import com.hearhere.presentation.features.main.MarkerDetailBottomSheet

class PostFinishActivity : BaseActivity<ActivityPostFinishBinding>(R.layout.activity_post_finish) {
    private val viewModel: PostFinishViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val posting : Posting = intent.getSerializableExtra("music") as Posting

        viewModel.getDetail(posting)
        observeViewModel()
        binding.viewModel = viewModel

        binding.postFinishBtn.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }

        }
    }

    override fun onCreateView(savedInstanceState: Bundle?) {


    }

    override fun registerViewModels(): List<BaseViewModel>  = listOf(viewModel)

    override fun observeViewModel() {
    }


}