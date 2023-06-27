package com.hearhere.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hearhere.presentation.common.util.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    AppCompatActivity() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    private var loadingDiaolog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this@BaseActivity
        onCreateView(savedInstanceState)

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
        if (loadingDiaolog !== null) return

        loadingDiaolog = LoadingDialog.newInstance()
        loadingDiaolog?.show(supportFragmentManager, "dialog")
    }

    private fun dismissLoading() {
        loadingDiaolog?.let { it.dismiss() }
        loadingDiaolog = null
    }

    override fun onDestroy() {
        super.onDestroy()
        // _binding = null
    }

    protected infix fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        this.observe(
            this@BaseActivity,
            Observer {
                block(it)
            }
        )
    }
}
