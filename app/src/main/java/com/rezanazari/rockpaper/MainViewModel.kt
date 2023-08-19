package com.rezanazari.rockpaper

import com.rezanazari.rockpaper.game.GameEngine
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.rezanazari.rockpaper.model.GameState

class MainViewModel : ViewModel() {

    private lateinit var gameEngine: GameEngine

    val state = mutableStateOf(GameState(gameItemList = emptyList()))
    private var screenWidth: Dp = 0.dp

    fun setScreenWidthAndInitializeItems(width: Dp) {
        this.screenWidth = width
        gameEngine = GameEngine(
            screenWidth,
            onUpdateGameList = { updateGameState(state.value.copy(gameItemList = it)) },
            onUpdateScoreBoard = { updateGameState(state.value.copy(scoreBoard = it)) },
        )
    }

    fun setGameEngine(gameEngine: GameEngine) {
        this.gameEngine = gameEngine
    }

    fun toggleGameRunningState() {
        if (state.value.isGameStarted) gameEngine.pauseGame()
        else gameEngine.startGame()
        updateGameState(state.value.copy(isGameStarted = !state.value.isGameStarted))
    }

    fun restartGame() {
        gameEngine.restartGame()
        state.value = state.value.copy(isGameStarted = false)
    }


    private fun updateGameState(state: GameState) {
        this.state.value = state
    }
}