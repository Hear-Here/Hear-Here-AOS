package com.hearhere.presentation.common.component.emojiButton

import com.hearhere.presentation.common.R


enum class GenreType(val kor : String) {
    BALLAD("발라드"), DANCE("댄스"), HIPHOP("랩/힙합"), INDIE("인디"), POP("팝"), BAND("밴드"), TROT("트로트")
}

fun GenreType.getResource() : Int = R.drawable.ic_headphone_black