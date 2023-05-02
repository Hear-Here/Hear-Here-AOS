package com.hearhere.presentation.common.component

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import com.hearhere.presentation.common.R
import com.hearhere.presentation.common.databinding.LayoutBasicTextFieldBinding

class BasicTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutBasicTextFieldBinding =
        LayoutBasicTextFieldBinding.inflate(LayoutInflater.from(context), this, true)


    var text: Editable
        get() {
            return binding.edittext.text
        }set(value) {
            binding.edittext.text = value
        }


    var placeholderText: CharSequence
        get() {
            return binding.edittext.hint
        }
        set(value) {
            binding.edittext.hint = value
        }

    var imeOptions: Int
        set(value) {
            binding.edittext.imeOptions = value
        }
        get() {
            return binding.edittext.imeOptions
        }

    init {
        binding.inputFieldStroke.visibility = INVISIBLE
        setStrokeListener()
        if(attrs != null) initAttr(attrs)

    }

    interface AfterTextChanged {
        fun afterTextChanged(s: Editable?)
    }

    interface BeforeTextChanged {
        fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
    }

    interface OnTextChanged {
        fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    }

    private fun initAttr(attrs : AttributeSet){
        if(attrs != null){
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.TextField)

            binding.edittext.setText(
                (typedArray.getString(R.styleable.TextField_android_text) ?: "")
            )
            placeholderText = typedArray.getString(R.styleable.TextField_android_hint) ?: ""
            imeOptions = typedArray.getInt(R.styleable.TextField_android_imeOptions, 0)
            typedArray.recycle()
        }
    }

    private fun setStrokeListener(){
        binding.edittext.setOnFocusChangeListener { v, hasFocus ->
            binding.inputFieldStroke.visibility = if (hasFocus) {
                VISIBLE
            } else {
                INVISIBLE
            }
        }

    }

    fun setText(text : String){
        binding.edittext.setText(text)
    }

    fun setOnEditorActionListener(onEditorActionListener: TextView.OnEditorActionListener) {
        binding.edittext.setOnEditorActionListener(onEditorActionListener)
    }


    private fun setTextFieldType(type: String) {
        when(type){
            "password" ->{
                binding.edittext.setTransformationMethod(PasswordTransformationMethod())
            }
            else ->{}
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textField: BasicTextField, text: String) {
            textField.setText(text)
        }

        @BindingAdapter("type")
        @JvmStatic
        fun setTextFieldType(view: BasicTextField, type: String) {
            view.setTextFieldType(type)
        }

        @JvmStatic
        @BindingAdapter("hint")
        fun setHint(textField: BasicTextField, text: String) {
            textField.placeholderText = text
        }

        @JvmStatic
        @BindingAdapter("android:onEditorAction")
        fun setOnEditorActionListener(
            textField: BasicTextField,
            onEditorActionListener: TextView.OnEditorActionListener
        ) {
            textField.binding.edittext.setOnEditorActionListener(onEditorActionListener)
        }


        @JvmStatic
        @BindingAdapter(
            value = ["android:beforeTextChanged", "android:onTextChanged", "android:afterTextChanged", "android:textAttrChanged"],
            requireAll = false
        )
        fun setTextWatcher(
            textField: BasicTextField,
            before: BeforeTextChanged?,
            on: OnTextChanged?,
            after: AfterTextChanged?,
            textAttrChanged: InverseBindingListener?
        ) {

            val newValue: TextWatcher? =
                if (before == null && after == null && on == null && textAttrChanged == null) {
                    null
                } else {
                    object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                            before?.beforeTextChanged(s, start, count, after)
                        }

                        override fun onTextChanged(
                            s: CharSequence,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            on?.onTextChanged(s, start, before, count)
                            textAttrChanged?.onChange()
                        }

                        override fun afterTextChanged(s: Editable) {
                            after?.afterTextChanged(s)
                        }
                    }
                }

            val oldValue = ListenerUtil.trackListener(
                textField,
                newValue,
                R.id.textWatcher
            )
            oldValue?.let {
                textField.binding.edittext.removeTextChangedListener(it)
            }
            newValue?.let { textField.binding.edittext.addTextChangedListener(it) }

        }
    }
}