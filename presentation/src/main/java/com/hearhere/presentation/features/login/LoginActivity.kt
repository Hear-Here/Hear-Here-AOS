package com.hearhere.presentation.features.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityLoginBinding
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(savedInstanceState: Bundle?) {
        Log.d("kakao", Utility.getKeyHash(this))
        binding.loginBtn.setOnClickListener {
            viewModel.kakaoLogin()
        }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf()

    override fun observeViewModel() {
        viewModel.loginState.observe {
            // Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            if (it == true) {
                Intent(this, OnBoardingActivity::class.java).also {
                    intent ->
                    startActivity(intent)
                }
            }
        }
    }
}
