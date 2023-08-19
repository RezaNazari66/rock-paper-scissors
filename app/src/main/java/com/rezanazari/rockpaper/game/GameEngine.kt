package com.rezanazari.rockpaper.game

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rezanazari.rockpaper.model.GameItem
import com.rezanazari.rockpaper.model.GameItemType
import com.rezanazari.rockpaper.model.ScoreBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class GameEngine(
    private val screenWidth: Dp,
    val onUpdateScoreBoard: (ScoreBoard?) -> Unit,
    val onUpdateGameList: (List<GameItem>) -> Unit,
) {
    companion object {
        const val BOARD_SIZE = 16
        const val MOVEMENT_DELAY_IN_MILLIS: Long = 1
        const val COLLIDE_DELAY_IN_MILLIS: Long = 1500
        const val BOARD_PADDING: Int = 16
    }

    private var tileSize = 0.dp
    private var isGameRunning = false
    private var gameItemList: List<GameItem> = mutableListOf()
    private var gameScope = CoroutineScope(Dispatchers.Default)

    init {
        initialGame()
    }

    private fun generateTileSize() {
        tileSize = screenWidth / BOARD_SIZE
    }

    private fun generateInitialItems() {
        gameItemList = listOf(
            createGameItem(GameItemType.SCISSOR),
            createGameItem(GameItemType.SCISSOR),
            createGameItem(GameItemType.SCISSOR),
            createGameItem(GameItemType.SCISSOR),
            createGameItem(GameItemType.SCISSOR),
            createGameItem(GameItemType.ROCK),
            createGameItem(GameItemType.ROCK),
            createGameItem(GameItemType.ROCK),
            createGameItem(GameItemType.ROCK),
            createGameItem(GameItemType.ROCK),
            createGameItem(GameItemType.PAPER),
            createGameItem(GameItemType.PAPER),
            createGameItem(GameItemType.PAPER),
            createGameItem(GameItemType.PAPER),
            createGameItem(GameItemType.PAPER),
        )
        onUpdateGameList(gameItemList)
    }

    fun restartGame() {
        gameScope.cancel()
        initialGame()
        pauseGame()
    }

    private fun initialGame() {
        generateTileSize()
        generateInitialItems()
    }


    private fun createGameItem(gameItemType: GameItemType) = GameItem(
        gameItemType,
        xPosition = getRandomPositionOnScreen(),
        yPosition = getRandomPositionOnScreen(),
        angle = getRandomAngleInRange()
    )

    private fun getRandomAngleInRange(): Int {
        return Random.nextInt(0, 180)
    }

    private fun getRandomPositionOnScreen(): Double {
        return Random.nextDouble(
            0.0 + BOARD_PADDING,
            (screenWidth.value - BOARD_PADDING).toDouble()
        )
    }

    private fun calculateNewItemPosition(gameItem: GameItem): GameItem {
        val newY = gameItem.yPosition - sin(Math.toRadians(gameItem.angle.toDouble()))
        val newX = gameItem.xPosition + cos(Math.toRadians(gameItem.angle.toDouble()))
        return gameItem.copy(xPosition = newX, yPosition = newY)
    }

    private fun moveAllItemsAndUpdatePositions(): MutableList<GameItem> {
        val newList = mutableListOf<GameItem>()

        gameItemList.forEach {
            val newAngle =
                GameUtils.calculateAdjustedBounceAngle(it, screenWidth, BOARD_PADDING)
            val item = if (newAngle == it.angle) {
                calculateNewItemPosition(it)
            } else {
                val new = it.copy(angle = newAngle)
                calculateNewItemPosition(new)
            }
            newList.add(item)
        }
        return newList
    }

    private fun determineLoserInCollision(first: GameItem, second: GameItem): GameItem {
        return when (Pair(first.type, second.type)) {
            GameItemType.ROCK to GameItemType.SCISSOR,
            GameItemType.SCISSOR to GameItemType.PAPER,
            GameItemType.PAPER to GameItemType.ROCK,
            -> second

            else -> first
        }
    }

    private suspend fun handleCollisionsAndRemoveItems(itemList: MutableList<GameItem>) {
        var removableItem: GameItem? = null
        val collidedPair = GameUtils.findCollidedItemsPair(itemList)
        if (collidedPair != null) {
            removableItem = determineLoserInCollision(collidedPair.first, collidedPair.second)
            val winner =
                if (collidedPair.first == removableItem) collidedPair.second else collidedPair.first
            onUpdateScoreBoard(
                ScoreBoard(
                    collidedPair.first,
                    collidedPair.second,
                    winner
                )
            )
            delay(COLLIDE_DELAY_IN_MILLIS)
        }
        this.gameItemList = itemList.filter { it != removableItem }
        onUpdateGameList(gameItemList)
        onUpdateScoreBoard(null)
    }

    fun startGame() {
        isGameRunning = true
        handleGameLoop()
    }

    fun pauseGame() {
        isGameRunning = false
    }

    private fun handleGameLoop() {
        gameScope = CoroutineScope(Dispatchers.Default)

        gameScope.launch {
            while (isGameRunning) {
                delay(MOVEMENT_DELAY_IN_MILLIS)
                val listAfterMovement = moveAllItemsAndUpdatePositions()
                handleCollisionsAndRemoveItems(listAfterMovement)
            }
        }
    }

}