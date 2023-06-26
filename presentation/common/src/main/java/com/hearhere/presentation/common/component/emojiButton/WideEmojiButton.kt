package com.hearhere.presentation.common.component.emojiButton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.databinding.LayoutWideEmojiButtonBinding

class WideEmojiButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutWideEmojiButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private fun setEmojiResource(@DrawableRes resourceId: Int) {
        setLayoutVisibility(binding.emojiLayout, true)
        binding.iconIv.setImageDrawable(AppCompatResources.getDrawable(context, resourceId))
    }

    private fun setLabelText(text: String) {
        binding.labelTv.text = text
    }

    private fun setLayoutVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) VISIBLE else GONE
    }

    companion object {
        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByWithType(view: WideEmojiButton, type: WithType?) {
            if (type == null) return
            view.setEmojiResource(type.getResource())
            view.setLabelText(type.kor)
        }

        @JvmStatic
        @BindingAdapter("emoji")
        fun setEmojiByGenreType(view: WideEmojiButton, type: GenreType?) {
            if (type == null) return
            view.setEmojiResource(type.getResource())
            view.setLabelText(type.kor)
        }
    }
}
