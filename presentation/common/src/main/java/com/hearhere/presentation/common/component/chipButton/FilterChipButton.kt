package com.hearhere.presentation.common.component.chipButton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.databinding.LayoutFilterChipButtonBinding

class FilterChipButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutFilterChipButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var type: ChipType = ChipType.GENRE
        set(value) {
            field = value
        }
        get() = field

    var label = ""
    init {
        binding.filterChipIv.visibility = View.GONE
    }
    fun setChipBtnClickListener(onClick: () -> Unit) {
        binding.root.setOnClickListener {
            onClick.invoke()
        }
    }

    fun setChipBtnClickListener(listener: FilterChipClickListener) {
        binding.root.setOnClickListener {
            listener.onClick(type, label)
        }
    }

    fun setChipImageResource(@DrawableRes resource: Int) {
        binding.filterChipIv.setImageResource(resource)
        binding.filterChipIv.visibility = View.VISIBLE
        binding.filterChipLabel.visibility = View.GONE
    }

    fun setChipImageInVisible() {
        binding.filterChipIv.visibility = View.GONE
        binding.filterChipLabel.visibility = View.VISIBLE
    }

    interface FilterChipClickListener {
        fun onClick(chipType: ChipType, typeName: String)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setChipOnClick(view: FilterChipButton, onClick: () -> Unit) {
            view.setChipBtnClickListener(onClick)
        }

        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setChipOnClick(view: FilterChipButton, clickListener: FilterChipClickListener) {
            view.setChipBtnClickListener(clickListener)
        }

        @JvmStatic
        @BindingAdapter("text")
        fun setFilterChipText(view: FilterChipButton, text: String) {
            view.binding.filterChipLabel.text = text
        }

        @JvmStatic
        @BindingAdapter("label")
        fun setFilterChipLabel(view: FilterChipButton, label: String) {
            view.label = label
        }

        @JvmStatic
        @BindingAdapter("chipType")
        fun setFilterChipType(view: FilterChipButton, chip: ChipType) {
            view.type = chip
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setChipEmoji(view: FilterChipButton, res: Int?) {
            if (res != null) {
                view.setChipImageResource(res)
            } else {
                view.setChipImageInVisible()
            }
        }
    }
}
