package com.hearhere.presentation.common.component

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.databinding.LayoutMarkerViewBinding

class MarkerImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutMarkerViewBinding = LayoutMarkerViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        binding.headphoneIv.visibility = GONE
    }

    fun setAlbumImage(uri: Uri?) {
        uri?.let {
            if (it.path.isNullOrBlank()) {
                binding.headphoneIv.visibility = VISIBLE
                binding.albumIv.visibility = GONE
            } else {
                binding.albumIv.visibility = VISIBLE
                binding.headphoneIv.visibility = GONE
            }

            Glide.with(this)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.headphones
                    )
                )
                .error(ContextCompat.getDrawable(context, R.drawable.headphones))
                .into(binding.albumIv)
        }
    }

    fun setMarkerFocus(isFocused: Boolean) {
        if (isFocused) {
            binding.root.backgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary)))
        } else binding.root.backgroundTintList = null
    }

    fun setMarkerBitmapImage(bitmap: Bitmap) {
        binding.albumIv.setImageBitmap(bitmap)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setAlbum(view: MarkerImageView, uri: Uri?) {
            view.setAlbumImage(uri)
        }
    }
}
