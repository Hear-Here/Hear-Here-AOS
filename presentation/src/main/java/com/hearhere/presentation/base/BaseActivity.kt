package com.hearhere.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    AppCompatActivity() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        _binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this@BaseActivity

        observeViewModel()
        observeLoadingState()
    }

    abstract fun onCreateView(savedInstanceState: Bundle?)

    abstract fun registerViewModels(): List<BaseViewModel>

    abstract fun observeViewModel()

    fun repeatOnStart(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    private fun observeLoadingState() {
        registerViewModels().forEach {
            it.loading.observe { loading ->
                if (loading) showLoading()
                else dismissLoading()
            }
        }
    }

    private fun showLoading() {
        //TODO 공용 Loading Dialog
    }

    private fun dismissLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected infix fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            block(it)
        })
    }

}