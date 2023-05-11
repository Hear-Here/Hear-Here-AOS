package com.hearhere.presentation.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.component.emojiButton.*
import com.hearhere.presentation.common.databinding.LayoutEmojiButtonBinding

class EmojiButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutEmojiButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private fun setEmojiResource(@DrawableRes resourceId :Int){
        setLayoutVisibility(binding.emojiLayout,true)
        setLayoutVisibility(binding.emotionLayout,false)
        binding.iconIv.setImageDrawable(AppCompatResources.getDrawable(context,resourceId))
    }

    private fun setLabelText(text : String){
        binding.labelTv.text = text
    }

    private fun setNonLabelResource(@DrawableRes resourceId :Int){
        setLayoutVisibility(binding.emojiLayout,false)
        setLayoutVisibility(binding.emotionLayout,true)
        binding.emotionIv.setImageDrawable(AppCompatResources.getDrawable(context,resourceId))
    }

    private fun setLayoutVisibility(view : View, visible : Boolean){
        view.visibility = if(visible) VISIBLE else GONE
    }

    companion object{
        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByWithType(view: EmojiButton, type : WithType ){
            view.setEmojiResource(type.getResource())
            view.setLabelText(type.kor)
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByWeatherType(view: EmojiButton, type : WeatherType){
            view.setNonLabelResource(type.getResource())
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByGenreType(view: EmojiButton, type :GenreType){
            view.setEmojiResource(type.getResource())
            view.setLabelText(type.kor)
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByEmotionType(view: EmojiButton, type :EmotionType){
            view.setNonLabelResource(type.getResource())
        }
    }
}