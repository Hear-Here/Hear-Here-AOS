package com.hearhere.presentation.common.component.emojiButton

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

abstract class WideEmojiButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        isSelected = false
    }

    fun setLayoutVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) VISIBLE else GONE
    }

    abstract fun setSelectedState()

    companion object {
        @JvmStatic
        @BindingAdapter("isSelected")
        fun setWideEmojiBtnSelected(view: WideEmojiButton, isSelected: Boolean) {
            view.isSelected = isSelected
            view.setSelectedState()
        }

        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setWideEmojiBtnOnClick(view: WideEmojiButton, onClick: () -> Unit) {
            view.setOnClickListener {
                onClick()
                view.isSelected = !view.isSelected
            }
        }
    }
}
