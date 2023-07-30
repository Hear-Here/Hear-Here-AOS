package com.hearhere.presentation.common.component.emojiButton

import com.hearhere.presentation.common.R

enum class WeatherType(val kor: String) {
    SUNNY("맑은 날"), NORMAL("선선한 날"), CLOUDY("구름 낀 날"), RAINY("비오는 날"), WINDY("바람 부는 날"), SNOWY("눈 내리는 날")
}

fun WeatherType.getResource(): Int {
    return when (this) {
        WeatherType.SUNNY -> R.drawable.sunny
        WeatherType.NORMAL -> R.drawable.normal
        WeatherType.CLOUDY -> R.drawable.cloudy
        WeatherType.RAINY -> R.drawable.rainy
        WeatherType.WINDY -> R.drawable.windy
        WeatherType.SNOWY -> R.drawable.snowy
    }
}
