package com.hearhere.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityNicknameBinding
import com.hearhere.presentation.databinding.ActivitySplashBinding
import com.hearhere.presentation.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetNickNameActivity : BaseActivity<ActivityNicknameBinding>(R.layout.activity_nickname) {
    private val viewModel :  SetNickNameViewModel by viewModels()

    override fun onCreateView(savedInstanceState: Bundle?) {
       binding.viewModel = viewModel
    }

    private fun navigateToMain(){
        MainActivity.start(this)
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.navigationToMain.observe {
            Log.d("nav", "nick name submit")
            navigateToMain()
        }
    }
}

