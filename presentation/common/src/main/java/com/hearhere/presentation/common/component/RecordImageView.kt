package com.hearhere.presentation.common.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.databinding.LayoutRecordImageViewBinding

class RecordImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding : LayoutRecordImageViewBinding = LayoutRecordImageViewBinding.inflate(
        LayoutInflater.from(context), this , true )


    init {
        val rotateAnimation = AnimationUtils.loadAnimation(context,R.anim.rotate).apply {
            this.repeatMode = Animation.RESTART
            this.repeatCount = Animation.INFINITE
            this.duration = 2000L
        }
        binding.root.startAnimation(rotateAnimation)

    }

    private fun setImageCover(uri : Uri?){
        uri?.let {
            if(it.path.isNullOrBlank()) {
                binding.recordInnerframeIv.visibility = View.VISIBLE
            }
            else{
                binding.recordInnerframeIv.visibility = View.INVISIBLE
            }

            Glide.with(this)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.outframe
                    )
                )
                .error(ContextCompat.getDrawable(context,  R.drawable.outframe))
                .into(binding.recordOutframeIv)
        }
    }

    private fun setFillHoleColor(hex:String){
        binding.recordFrameHoleIv.background = ContextCompat.getDrawable(context,  R.drawable.innerframe)
    }


    companion object{
        @JvmStatic
        @BindingAdapter("android:src")
        fun setOutframe(view : RecordImageView ,uri : Uri?){
            Log.d("setUri",uri.toString())
            view.setImageCover(uri)
        }

        @JvmStatic
        @BindingAdapter("holeColor")
        fun setBackground(view : RecordImageView , hex : String){
           view.setFillHoleColor(hex)
        }
    }

}