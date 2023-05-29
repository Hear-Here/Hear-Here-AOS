package com.hearhere.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityNicknameBinding
import com.hearhere.presentation.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetNickNameActivity : BaseActivity<ActivityNicknameBinding>(R.layout.activity_nickname) {
    private val viewModel :  SetNickNameViewModel by viewModels()

    override fun onCreateView(savedInstanceState: Bundle?) {
       binding.viewModel = viewModel
       binding.startBtn.setOnClickListener {
           navigateToMain()
       }
    }

    private fun navigateToMain(){
//        Intent(this,LoginActivity::class.java).also {
//            startActivity(it)
//        }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {

    }
}

