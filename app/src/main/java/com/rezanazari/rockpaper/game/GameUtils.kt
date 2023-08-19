package com.rezanazari.rockpaper.game

import androidx.compose.ui.unit.Dp
import com.rezanazari.rockpaper.model.GameItem

object GameUtils {

    fun findCollidedItemsPair(itemList: MutableList<GameItem>): Pair<GameItem, GameItem>? {
        for (i in 0 until itemList.size - 1) {
            for (j in i + 1 until itemList.size) {
                if (areItemsColliding(itemList[i], itemList[j])) {
                    return itemList[i] to itemList[j]
                }
            }
        }
        return null
    }

    private fun areItemsColliding(item1: GameItem, item2: GameItem): Boolean {
        return item1.xPosition.toInt() == item2.xPosition.toInt()
                && item1.yPosition.toInt() == item2.yPosition.toInt()
                && item1 != item2
    }

    fun calculateAdjustedBounceAngle(item: GameItem, screenWidth: Dp, padding:Int): Int {
        val maxPosition = screenWidth.value - padding
        if (item.xPosition <= padding || (item.xPosition >= maxPosition)) {
            return 180 - item.angle
        }
        if (item.yPosition <= padding || (item.yPosition >= maxPosition)) {
            return item.angle * -1
        }
        return item.angle
    }

}