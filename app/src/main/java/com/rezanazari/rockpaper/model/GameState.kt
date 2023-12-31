package com.rezanazari.rockpaper.model

data class GameState(
    val gameItemList: List<GameItem>,
    val scoreBoard: ScoreBoard? = null,
    val isGameStarted: Boolean = false,
)

