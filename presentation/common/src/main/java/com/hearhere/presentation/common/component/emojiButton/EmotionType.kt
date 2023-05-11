package com.hearhere.presentation.common.component.emojiButton

import com.hearhere.presentation.common.R

enum class EmotionType {
    SMILE, SOSO, SAD, FUNNY, HEART, ANGRY
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