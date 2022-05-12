package ru.mobileup.features.collection.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// TODO: add cutout button
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomBarItemConfig>,
    onItemClick: (pos: Int) -> Unit,
) {
    val backgroundColor = Color.Transparent
    BottomAppBar(
        modifier = modifier,
        backgroundColor = backgroundColor,
        elevation = 0.dp,
    ) {
        BottomBarContentIcons(backgroundColor, items, onItemClick)
    }
}

@Composable
fun BottomBarContentIcons(
    backgroundColor: Color,
    items: List<BottomBarItemConfig>,
    onItemClick: (pos: Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backgroundColor),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.forEachIndexed { index, item ->
            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                onClick = { onItemClick(index) }
            ) {
                Icon(
                    modifier = Modifier.size(item.iconSize),
                    painter = item.painter,
                    contentDescription = item.contentDescription
                )
            }
        }
    }
}

class BottomBarItemConfig(
    val painter: Painter,
    val contentDescription: String = "",
    val iconSize: Dp = 32.dp
)