package com.hearhere.presentation.common.util

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.hearhere.presentation.common.R

class LoadingDialog: DialogFragment(){

    companion object {
        fun newInstance() = LoadingDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.run {
            window?.run {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            setCanceledOnTouchOutside(false)
        }
        isCancelable = false

        try{
            context?.let {
                val layout = LinearLayout(it)
                val progress = LottieAnimationView(it)
                progress.setAnimation(R.raw.loading)
                progress.repeatMode = LottieDrawable.RESTART
                progress.repeatCount = LottieDrawable.INFINITE

                layout.addView(progress)
                progress.playAnimation()

                return layout
            }
        }catch (e: OutOfMemoryError) {

        } catch (e: Exception) {

        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}