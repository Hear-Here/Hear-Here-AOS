package com.hearhere.presentation.common.component.emojiButton

import com.hearhere.presentation.common.R

enum class GenreType(val kor: String) {
    BALLAD("발라드"), DANCE("댄스"), RB("R&B"), HIPHOP("랩/힙합"), INDIE("인디"), POP("POP"), BAND("밴드"), TROT("트로트")
}

fun GenreType.getResource(): Int {
    return when (this) {
        GenreType.BALLAD -> R.drawable.ballad
        GenreType.DANCE -> R.drawable.dance
        GenreType.HIPHOP -> R.drawable.hiphop
        GenreType.RB -> R.drawable.rnb
        GenreType.INDIE -> R.drawable.indie
        GenreType.POP -> R.drawable.pop
        GenreType.BAND -> R.drawable.band
        GenreType.TROT -> R.drawable.trot
    }
}
