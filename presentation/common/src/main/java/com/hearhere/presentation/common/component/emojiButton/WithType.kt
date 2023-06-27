package com.hearhere.presentation.common.component.emojiButton

import com.hearhere.presentation.common.R

enum class WithType(val kor: String) {
    ALONE("혼자"), FRIEND("친구"), COUPLE("연인"), FAMILY("가족"), PET("반려동물"), SOMEBODY("누군가")
}

fun WithType.getResource(): Int = R.drawable.ic_with_heart
