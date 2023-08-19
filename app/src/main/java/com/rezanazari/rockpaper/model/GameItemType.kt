package com.rezanazari.rockpaper.model

import androidx.compose.ui.graphics.Color

enum class GameItemType(val itemName: String, val color: Color) {
    ROCK("R", Color.Blue),
    PAPER("P", Color.Red),
    SCISSOR("S", Color.Green)
}
