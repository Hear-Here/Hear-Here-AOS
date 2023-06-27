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

    companion object {
//        @JvmStatic
//        @BindingAdapter("emoji")
//        fun setEmojiByWithType(view: WideEmojiButton, type: WithType?) {
//            if (type == null) return
//            view.setEmojiResource(type.getResource())
//            view.setLabelText(type.kor)
//        }
//
//        @JvmStatic
//        @BindingAdapter("emoji")
//        fun setEmojiByGenreType(view: WideEmojiButton, type: GenreType?) {
//            if (type == null) return
//            view.setEmojiResource(type.getResource())
//            view.setLabelText(type.kor)
//        }

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
