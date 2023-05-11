package com.hearhere.presentation.common.component.emojiButton

import com.hearhere.presentation.common.R

enum class WeatherType {
    SUNNY, NORMAL, CLOUDY, RAINY, WINDY, SNOWY
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

