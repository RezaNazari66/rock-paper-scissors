package com.rezanazari.rockpaper.model

data class GameItem(
    val type: GameItemType,
    var angle: Int,
    val xPosition: Double,
    val yPosition: Double,
)