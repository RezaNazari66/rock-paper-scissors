package com.rezanazari.rockpaper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rezanazari.rockpaper.model.GameState


@Composable
fun Board(state: GameState, setScreenWidth: (Dp) -> Unit) {
    BoxWithConstraints(Modifier.padding(16.dp)) {

        LaunchedEffect(key1 = Unit, block = {
            setScreenWidth(maxWidth)
        })
        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, MaterialTheme.colorScheme.tertiary)
        )
        state.gameItemList.forEach {
            Box(
                modifier = Modifier
                    .offset(x = it.xPosition.dp, y = it.yPosition.dp)
                    .background(
                        it.type.color
                    )
            ) {
                Text(
                    it.type.itemName,
                    fontSize = 8.sp,
                    color = Color.White
                )
            }
        }
    }
}