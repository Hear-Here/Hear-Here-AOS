package com.hearhere.presentation.common.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.databinding.LayoutBasicButtonBinding

class BasicButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutBasicButtonBinding =
        LayoutBasicButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if (attrs != null) initAttr(attrs)
    }

    private fun initAttr(attrs: AttributeSet) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BasicButton)

        binding.button.text = typedArray.getString(R.styleable.BasicButton_android_text) ?: ""

        binding.button.setTextColor(
            (resources.getColorStateList(typedArray.getResourceId(R.styleable.BasicButton_android_textColor, 0), null))
        )

        binding.button.setBackgroundResource(
            (typedArray.getResourceId(R.styleable.BasicButton_android_background, 0))
        )

        binding.button.isEnabled = typedArray.getBoolean(R.styleable.BasicButton_android_enabled, true)
        binding.button.layoutParams.apply {
            height = typedArray.getLayoutDimension(R.styleable.BasicButton_android_layout_height, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        typedArray.recycle()
    }

    fun setText(text: String) {
        binding.button.text = text
    }

    fun setTextColor(textColor: Int) {
        binding.button.setTextColor(textColor)
    }

    fun setBackground(background: Int) {
        binding.button.setBackgroundResource(background)
    }

    fun setButtonEnabled(isEnabled: Boolean) {
        binding.button.isEnabled = isEnabled
    }

    // 함수 작성 시 이름이 기존의 JVM에 있는 것과 동일하게 되지 않도록 주의할 것
    private fun setButtonClickListener(listener: OnClickListener) {
        binding.button.setOnClickListener {
            listener.onClick(it)
            it.isSelected = !it.isSelected
        }
    }
    @JvmName("basicbutton-listener")
    private fun setButtonClickListener(listener: () -> Unit) {
        binding.button.setOnClickListener {
            listener.invoke()
        }
    }
    companion object {

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(button: BasicButton, text: String) {
            button.setText(text)
        }

        @JvmStatic
        @BindingAdapter("android:textColor")
        fun setTextColor(button: BasicButton, textColor: Int) {
            button.setTextColor(textColor)
        }

        @JvmStatic
        @BindingAdapter("android:background")
        fun setBackground(button: BasicButton, background: Int) {
            button.setBackground(background)
        }

        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setOnClick(button: BasicButton, listener: OnClickListener) {
            button.setButtonClickListener(listener) // View.OnClickListener
        }

        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setOnClick(button: BasicButton, listener: () -> Unit) {
            button.setButtonClickListener(listener) // View.OnClickListener
        }

        @JvmStatic
        @BindingAdapter("android:enabled")
        fun setEnabled(button: BasicButton, isEnabled: Boolean) {
            button.setButtonEnabled(isEnabled)
        }
    }
}
