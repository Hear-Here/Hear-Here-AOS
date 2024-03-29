package com.hearhere.presentation.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.common.component.emojiButton.WithType
import com.hearhere.presentation.common.component.emojiButton.getResource
import com.hearhere.presentation.common.databinding.LayoutSquareChipButtonBinding
import com.hearhere.presentation.common.databinding.LayoutSqureButtonBinding

class SquareButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutSqureButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private fun setEmojiResource(@DrawableRes resourceId: Int) {
        setLayoutVisibility(binding.emojiLayout, true)
        setLayoutVisibility(binding.emotionLayout, false)
        binding.iconIv.setImageDrawable(AppCompatResources.getDrawable(context, resourceId))
    }

    private fun setLabelText(text: String) {
        binding.labelTv.text = text
    }

    private fun setNonLabelResource(@DrawableRes resourceId: Int) {
        setLayoutVisibility(binding.emojiLayout, false)
        setLayoutVisibility(binding.emotionLayout, true)
        binding.emotionIv.setImageDrawable(AppCompatResources.getDrawable(context, resourceId))
    }

    private fun setLayoutVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) VISIBLE else GONE
    }

    companion object {
        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByWithType(view: SquareButton, type: WithType?) {
            if (type == null) return
            view.setEmojiResource(R.drawable.ic_with_heart)
            view.setLabelText(type.kor)
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByWeatherType(view: SquareButton, type: WeatherType?) {
            if (type == null) return
            view.setNonLabelResource(type.getResource())
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByGenreType(view: SquareButton, type: GenreType?) {
            if (type == null) return
            view.setEmojiResource(R.drawable.ic_headphone_black)
            view.setLabelText(type.kor)
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByEmotionType(view: SquareButton, type: EmotionType?) {
            if (type == null) return
            view.setNonLabelResource(type.getResource())
        }

        @JvmStatic
        @BindingAdapter("isSelected")
        fun setSquareBtnSelected(view: SquareButton, selected : Boolean) {
           view.isSelected = selected
        }
    }
}
