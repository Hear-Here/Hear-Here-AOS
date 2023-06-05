package com.hearhere.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreateView(savedInstanceState: Bundle?) {
        Log.d("kakao",Utility.getKeyHash(this))
        binding.loginBtn.setOnClickListener {
            viewModel.kakaoLogin()
        }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf()

    override fun observeViewModel() {
        viewModel.loginState.observe {
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            if(it == true) {
                Intent(this,OnBoardingActivity::class.java).also {
                    intent -> startActivity(intent)
                }
            }
        }
    }

}