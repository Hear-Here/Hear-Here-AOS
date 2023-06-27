package com.hearhere.presentation.common.component.emojiButton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.databinding.LayoutGenreButtonBinding

class GenreWideButton@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WideEmojiButton(context, attrs, defStyleAttr) {

    val binding = LayoutGenreButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var genre: GenreType? = null
        set(value) {
            field = value
        } get() = field

    interface GenreOnClickListener {
        fun onClick(g: GenreType)
    }

    fun setEmojiResource(@DrawableRes resourceId: Int) {
        setLayoutVisibility(binding.emojiLayout, true)
        binding.iconIv.setImageDrawable(AppCompatResources.getDrawable(context, resourceId))
    }

    fun setLabelText(text: String) {
        binding.labelTv.text = text
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setWideEmojiBtnOnClick(view: GenreWideButton, onClick: (GenreType) -> Unit) {
            if (view.genre == null) return
            view.setOnClickListener {
                onClick(view.genre!!)
                view.isSelected = !view.isSelected
            }
        }

        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setWideEmojiBtnOnClick(view: GenreWideButton, listener: GenreOnClickListener) {
            if (view.genre == null) return
            view.setOnClickListener {
                listener.onClick(view.genre!!)
                view.isSelected = !view.isSelected
            }
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setGenreEmoji(view: GenreWideButton, type: GenreType) {
            view.genre = type
            if (type == null) return
            view.setEmojiResource(type.getResource())
            view.setLabelText(type.kor)
        }
    }
}
