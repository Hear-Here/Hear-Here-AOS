package com.hearhere.presentation.common.component.emojiButton

import com.hearhere.presentation.common.R

enum class EmotionType(val kor: String) {
    SMILE("행복한 기분"), SOSO("보통의 기분"), SAD("기분 슬픔"), FUNNY("즐거운 기분"), HEART("사랑에 빠진 기분"), ANGRY("화가난 기분")
}

fun EmotionType.getResource(): Int {
    return when (this) {
        EmotionType.SMILE -> R.drawable.smile
        EmotionType.SOSO -> R.drawable.soso
        EmotionType.SAD -> R.drawable.sad
        EmotionType.FUNNY -> R.drawable.funny
        EmotionType.HEART -> R.drawable.heart
        EmotionType.ANGRY -> R.drawable.angry
    }
}
