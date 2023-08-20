package com.rezanazari.rockpaper.game

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rezanazari.rockpaper.model.GameItem
import com.rezanazari.rockpaper.model.GameItemType
import org.junit.Assert.*
import org.junit.Test

class GameUtilsTest {

    @Test
    fun `test findCollidedItemsPair when collision exists`() {
        val item1 =
            GameItem(xPosition = 10.0, yPosition = 20.0, angle = 0, type = GameItemType.PAPER)
        val item2 =
            GameItem(xPosition = 10.0, yPosition = 20.0, angle = 0, type = GameItemType.ROCK)
        val itemList = mutableListOf(item1, item2)

        val collidedPair = GameUtils.findCollidedItemsPair(itemList)

        assertEquals(Pair(item1, item2), collidedPair)
    }

    @Test
    fun `test findCollidedItemsPair when two items are same `() {
        val item =
            GameItem(xPosition = 10.0, yPosition = 20.0, angle = 0, type = GameItemType.PAPER)
        val itemList = mutableListOf(item, item)

        val collidedPair = GameUtils.findCollidedItemsPair(itemList)

        assertEquals(null, collidedPair)
    }

    @Test
    fun `test findCollidedItemsPair when list is empty `() {
        val itemList = mutableListOf<GameItem>()

        val collidedPair = GameUtils.findCollidedItemsPair(itemList)

        assertEquals(null, collidedPair)
    }

    @Test
    fun `test findCollidedItemsPair when no collision exists`() {
        val item1 =
            GameItem(xPosition = 10.0, yPosition = 20.0, angle = 0, type = GameItemType.PAPER)
        val item2 =
            GameItem(xPosition = 30.0, yPosition = 40.0, angle = 0, type = GameItemType.SCISSOR)
        val itemList = mutableListOf(item1, item2)

        val collidedPair = GameUtils.findCollidedItemsPair(itemList)

        assertEquals(null, collidedPair)
    }

    @Test
    fun `test findCollidedItemsPair with multiple items`() {
        val item1 =
            GameItem(xPosition = 10.0, yPosition = 20.0, angle = 0, type = GameItemType.ROCK)
        val item2 =
            GameItem(xPosition = 10.0, yPosition = 20.0, angle = 0, type = GameItemType.PAPER)
        val item3 =
            GameItem(xPosition = 30.0, yPosition = 40.0, angle = 0, type = GameItemType.SCISSOR)
        val item4 =
            GameItem(xPosition = 10.0, yPosition = 20.0, angle = 0, type = GameItemType.PAPER)
        val itemList = mutableListOf(item1, item2, item3, item4)

        val collidedPair = GameUtils.findCollidedItemsPair(itemList)

        assertEquals(Pair(item1, item2), collidedPair)
    }


    @Test
    fun `test calculateAdjustedBounceAngle when xPosition is less than bounds`() {
        val screenWidth: Dp = 800.dp
        val padding = 16
        val item =
            GameItem(xPosition = 5.0, yPosition = 20.0, angle = 45, type = GameItemType.PAPER)
        val adjustedAngle = GameUtils.calculateAdjustedBounceAngle(item, screenWidth, padding)

        assertEquals(135, adjustedAngle)
    }

    @Test
    fun `test calculateAdjustedBounceAngle when xPosition is more than bounds`() {
        val screenWidth: Dp = 800.dp
        val padding = 16
        val item =
            GameItem(xPosition = 810.0, yPosition = 20.0, angle = 45, type = GameItemType.PAPER)
        val adjustedAngle = GameUtils.calculateAdjustedBounceAngle(item, screenWidth, padding)

        assertEquals(135, adjustedAngle)
    }

    @Test
    fun `test calculateAdjustedBounceAngle when yPosition is less than bounds`() {
        val screenWidth: Dp = 800.dp
        val padding = 16
        val item =
            GameItem(xPosition = 100.0, yPosition = 5.0, angle = 45, type = GameItemType.PAPER)
        val adjustedAngle = GameUtils.calculateAdjustedBounceAngle(
            item, screenWidth, padding
        )

        assertEquals(-45, adjustedAngle)
    }

    @Test
    fun `test calculateAdjustedBounceAngle when yPosition is more than bounds`() {
        val screenWidth: Dp = 800.dp
        val padding = 16
        val item =
            GameItem(xPosition = 100.0, yPosition = 800.0, angle = 45, type = GameItemType.PAPER)
        val adjustedAngle = GameUtils.calculateAdjustedBounceAngle(
            item, screenWidth, padding
        )

        assertEquals(-45, adjustedAngle)
    }

    @Test
    fun `test calculateAdjustedBounceAngle within bounds`() {
        val screenWidth: Dp = 800.dp
        val padding = 16
        val item =
            GameItem(xPosition = 100.0, yPosition = 200.0, angle = 45, type = GameItemType.ROCK)
        val adjustedAngle = GameUtils.calculateAdjustedBounceAngle(item, screenWidth, padding)

        assertEquals(45, adjustedAngle)
    }
}