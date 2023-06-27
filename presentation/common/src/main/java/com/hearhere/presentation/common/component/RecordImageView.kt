package com.hearhere.presentation.common.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.databinding.LayoutRecordImageViewBinding
import com.hearhere.presentation.util.ConvertDPtoPX

class RecordImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutRecordImageViewBinding = LayoutRecordImageViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var uri: Uri? = null
        set(value) {
            uri = value
        }

    init {
        val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate).apply {
            this.repeatMode = Animation.RESTART
            this.repeatCount = Animation.INFINITE
            this.duration = 2000L
        }
        binding.root.startAnimation(rotateAnimation)
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DefaultView)

        typedArray.recycle()
    }

    fun setImageCover(uri: Uri?) {
        uri?.let {
            if (it.path.isNullOrBlank()) {
                binding.recordInnerLayout.visibility = View.INVISIBLE
                binding.recordInnerframeIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outframe))
                return
            } else {
                binding.recordHoleLayout.visibility = View.VISIBLE
            }

            Glide.with(this)
                .load(it)
                // .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.outframe
                    )
                )
                .error(ContextCompat.getDrawable(context, R.drawable.outframe))
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        binding.recordOutframeIv.setImageDrawable(resource)
                        invalidate()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
    }
    private fun setFillHoleColor(hex: String) {
        binding.recordFrameHoleIv.background = ContextCompat.getDrawable(context, R.drawable.innerframe)
    }

    private fun setRecordType(type: String) {
        when (type) {
            "small" -> {
                binding.recordFrameLayout.layoutParams.apply {
                    width = ConvertDPtoPX(context, 145)
                    height = ConvertDPtoPX(context, 145)
                }

                binding.recordHoleLayout.layoutParams.apply {
                    width = ConvertDPtoPX(context, 40)
                    height = ConvertDPtoPX(context, 40)
                }

//                binding.recordInnerLayout.layoutParams.apply {
//                    width = ConvertDPtoPX(context,110)
//                    height = ConvertDPtoPX(context,110)
//                }
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setOutframe(view: RecordImageView, uri: Uri?) {
            view.setImageCover(uri)
        }

        @JvmStatic
        @BindingAdapter("type")
        fun setRecordType(view: RecordImageView, type: String) {
            view.setRecordType(type)
        }
    }
}
