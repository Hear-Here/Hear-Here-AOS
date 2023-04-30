package com.hearhere.presentation.features.main

import android.os.Bundle
import androidx.activity.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityMainBinding
import com.hearhere.presentation.databinding.ActivityTestBinding

class MainActivity :BaseActivity<ActivityMainBinding>(R.layout.activity_main){

    private val viewModel : MainViewModel by viewModels()

    override fun onCreateView(savedInstanceState: Bundle?) {
    }

    override fun registerViewModels(): List<BaseViewModel>  = listOf(viewModel)

    override fun observeViewModel() {
    }
}