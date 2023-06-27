package com.hearhere.presentation.common.component.chipButton

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.component.emojiButton.GenreType
import com.hearhere.presentation.common.component.emojiButton.WithType
import com.hearhere.presentation.common.databinding.LayoutChipButtonBinding

class ChipButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutChipButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var type: ChipType = ChipType.GENRE
        set(value) {
            field = value
        }
        get() = field

    var label = ""

    init {
        setSelectedState()
    }

    fun setChipBtnClickListener(onClick: () -> Unit) {
        binding.root.setOnClickListener {
            // isSelected = !isSelected
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

    @SuppressLint("ResourceAsColor")
    fun setSelectedState() {
        when {
            isSelected -> {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.chip_button_background_selected) as GradientDrawable
                binding.chipLabel.setTextColor(ContextCompat.getColor(context, R.color.white))
            }

            else -> {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.chip_button_background_unselected) as GradientDrawable
                binding.chipLabel.setTextColor(ContextCompat.getColor(context, R.color.gray50))
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
        fun setChipOnClick(view: ChipButton, onClick: ChipClickListener) {
            view.setChipBtnClickListener(onClick)
        }

        @JvmStatic
        @BindingAdapter("text")
        fun setChipLabel(view: ChipButton, text: String) {
            view.binding.chipLabel.text = text
        }

        @JvmStatic
        @BindingAdapter("chip")
        fun setChipLabelWithType(view: ChipButton, genre: GenreType) {
            view.binding.chipLabel.text = genre.kor
            view.label = genre.name
            view.type = ChipType.GENRE
        }

        @JvmStatic
        @BindingAdapter("chip")
        fun setChipLabelWithType(view: ChipButton, with: WithType) {
            view.binding.chipLabel.text = with.kor
            view.label = with.name
            view.type = ChipType.WITH
        }

        @JvmStatic
        @BindingAdapter("isSelected")
        fun setChipSelectedState(view: ChipButton, isSelected: Boolean) {
            view.isSelected = isSelected
            view.setSelectedState()
        }
    }
}
