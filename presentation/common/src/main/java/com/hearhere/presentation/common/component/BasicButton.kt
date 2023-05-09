package com.hearhere.presentation.common.component

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.databinding.LayoutBasicButtonBinding

class BasicButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding:  LayoutBasicButtonBinding =
        LayoutBasicButtonBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        if(attrs != null) initAttr(attrs)
    }


    private fun initAttr(attrs: AttributeSet) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BasicButton)

        binding.textview.text = typedArray.getString(R.styleable.BasicButton_android_text) ?: ""

        binding.textview.setTextColor(
            (typedArray.getColor(R.styleable.BasicButton_android_textColor, 0))
        )

        binding.buttonBackground.layoutParams.width =
            typedArray.getLayoutDimension(R.styleable.BasicButton_android_layout_width, 0)

        binding.buttonBackground.layoutParams.height =
            typedArray.getLayoutDimension(R.styleable.BasicButton_android_layout_height, 0)

//        binding.buttonBackground.setBackgroundResource(
//            typedArray.getResourceId(R.styleable.BasicButton_android_background, 0)
//        )

        typedArray.recycle()
    }

    fun setText(text: String){
        binding.textview.text = text
    }

    fun setTextColor(textColor: Int){
        binding.textview.setTextColor(textColor)
    }

    fun setLayoutWidth(layout_width: Int){
        val layoutParams = binding.buttonBackground.layoutParams
        layoutParams.width = width.toInt()
        layoutParams.height = height.toInt()
        binding.buttonBackground.layoutParams = layoutParams
    }

    fun setLayoutHeight(layout_height: Int){
        val layoutParams = binding.buttonBackground.layoutParams
        layoutParams.width = width.toInt()
        layoutParams.height = height.toInt()
        binding.buttonBackground.layoutParams = layoutParams
    }


    fun setBackground(background: Int){
        binding.buttonBackground.setBackgroundResource(background)
    }



    companion object{

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
        @BindingAdapter("android:layout_width")
        fun setLayoutWidth(button: BasicButton, layout_width: Int) {
            button.setLayoutWidth(layout_width)
        }

        @JvmStatic
        @BindingAdapter("android:layout_height")
        fun setLayoutHeight(button: BasicButton, layout_height: Int) {
            button.setLayoutHeight(layout_height)
        }

//        @JvmStatic
//        @BindingAdapter("android:background")
//        fun setBackground(button: BasicButton, background: Int){
//            button.setBackground(background)
//        }

        @JvmStatic
        @BindingAdapter("android:onClick")
        fun setOnClick(button: BasicButton, onClickListener: OnClickListener?) {
            button.binding.root.setOnClickListener(onClickListener)
            button.apply {
                isSelected = !isSelected
                Log.d("효민", isSelected.toString())
            }
        }


    }


}
