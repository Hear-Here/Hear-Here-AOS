package com.hearhere.presentation.common.component.chipButton

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.component.emojiButton.EmotionType
import com.hearhere.presentation.common.component.emojiButton.WeatherType
import com.hearhere.presentation.common.component.emojiButton.getResource
import com.hearhere.presentation.common.databinding.LayoutSquareChipButtonBinding

class SquareChipButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutSquareChipButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var type: ChipType = ChipType.GENRE
        set(value) {
            field = value
        }
        get() = field

    var label = ""

    fun setChipBtnClickListener(onClick: () -> Unit) {
        binding.root.setOnClickListener {
            isSelected = !isSelected
            onClick.invoke()
            setSelectedState()
        }
    }

    fun setChipBtnClickListener(onClick: ChipClickListener) {
        binding.root.setOnClickListener {
            isSelected = !isSelected
            onClick.onClickChip(this.type, this.label)
            setSelectedState()
        }
    }

    fun setChipEmoji(type: ChipType, @DrawableRes res : Int ) {
        binding.chipIv.setImageResource(res)
    }

    @SuppressLint("ResourceAsColor")
    fun setSelectedState() {
        when {
            isSelected -> {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.square_chip_button_background_selected) as GradientDrawable
            }

            else -> {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.square_chip_button_background_unselected) as GradientDrawable
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setChipOnClick(view: ChipButton, onClick: () -> Unit) {
            view.setChipBtnClickListener(onClick)
        }

        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setChipOnClick(view: SquareChipButton, onClick: ChipClickListener) {
            view.setChipBtnClickListener(onClick)
        }

        @JvmStatic
        @BindingAdapter("chip")
        fun setChipLabelWithType(view: SquareChipButton, emotion: EmotionType) {
            view.type = ChipType.EMOTION
            view.label = emotion.name
            view.setChipEmoji(view.type, emotion.getResource())
        }

        @JvmStatic
        @BindingAdapter("chip")
        fun setChipLabelWithType(view: SquareChipButton, weather: WeatherType) {
            view.type = ChipType.WEATHER
            view.label = weather.name
            view.setChipEmoji(view.type, weather.getResource())
        }

        @JvmStatic
        @BindingAdapter("isSelected")
        fun setChipSelectedState(view: SquareChipButton, isSelected: Boolean) {
            view.isSelected = isSelected
            view.setSelectedState()
        }
    }
}
