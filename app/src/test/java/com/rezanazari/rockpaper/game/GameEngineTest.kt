package com.rezanazari.rockpaper.game

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rezanazari.rockpaper.model.GameItem
import com.rezanazari.rockpaper.model.ScoreBoard
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class GameEngineTest {

    private lateinit var onUpdateScoreBoard: (ScoreBoard?) -> Unit
    private lateinit var onUpdateGameList: (List<GameItem>) -> Unit

    private lateinit var gameEngine: GameEngine

    @Before
    fun setup() {
        onUpdateScoreBoard = mockk(relaxed = true)
        onUpdateGameList = mockk(relaxed = true)

        val screenWidth: Dp = 800.dp
        gameEngine = GameEngine(screenWidth, onUpdateScoreBoard, onUpdateGameList)
    }


    @Test
    fun `test startGame and pauseGame`() {
        gameEngine.startGame()
        Thread.sleep(100)
        gameEngine.pauseGame()

        verify(atLeast = 1) { onUpdateGameList(any()) }
        verify(atLeast = 1) { onUpdateScoreBoard(any()) }
    }

    @Test
    fun `test restartGame`() {
        gameEngine.pauseGame()
        gameEngine.restartGame()
        verify(atLeast = 1) { onUpdateGameList(any()) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}