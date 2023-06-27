package com.hearhere.presentation.features.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    override fun onCreateView(savedInstanceState: Bundle?) {
        startAnimation()
    }

    override fun onRestart() {
        super.onRestart()
        startAnimation()
    }

    private fun startAnimation() {
        lifecycleScope.launch {
            with(this@OnBoardingActivity) {
                binding.welcomeTv.visibility = View.INVISIBLE
                delay(1000)
                binding.welcomeTv.visibility = View.VISIBLE
                startAnimationWithShow(this, binding.welcomeTv, R.anim.enter)
                delay(2000)
                startAnimationWithHide(this, binding.welcomeTv, R.anim.exit)

                delay(2000)
                binding.welcomeTv.setText(R.string.welcome_second)
                startAnimationWithShow(this, binding.welcomeTv, R.anim.enter)
                delay(2000)
                startAnimationWithHide(this, binding.welcomeTv, R.anim.exit)
                delay(2000)

                Intent(this, SetNickNameActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf()

    override fun observeViewModel() {
    }

    fun startAnimationWithShow(context: Context, view: View, id: Int) {
        view.visibility = View.VISIBLE // 애니메이션 전에 뷰를 보이게 한다
        view.startAnimation(AnimationUtils.loadAnimation(context, id)) // 애니메이션 설정&시작
    }

    fun startAnimationWithHide(context: Context, view: View, id: Int) {
        val exitAnim = AnimationUtils.loadAnimation(context, id) // 애니메이션 설정
        exitAnim.setAnimationListener(HideAnimListener(view)) // 리스너를 통해 애니메이션이 끝나면 뷰를 감춘다
        view.startAnimation(exitAnim) // 애니메이션 시작
    }

    inner class HideAnimListener(private val view: View) : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {}

        // 애니메이션이 끝나면 뷰를 감춘다
        override fun onAnimationEnd(p0: Animation?) {
            view.visibility = View.GONE
        }

        override fun onAnimationRepeat(p0: Animation?) {}
    }
}
