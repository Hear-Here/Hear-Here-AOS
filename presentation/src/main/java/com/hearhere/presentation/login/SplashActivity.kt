package com.hearhere.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivitySplashBinding
import com.hearhere.presentation.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel : RoutingViewModel by viewModels()

    override fun onCreateView(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            delay(3000)
            viewModel.checkToken()
        }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.navigateToLoginEvent.observe{
            Intent(this,LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        viewModel.navigateToMainEvent.observe{
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}