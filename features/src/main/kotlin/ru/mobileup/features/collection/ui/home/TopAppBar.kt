package ru.mobileup.features.collection.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.mobileup.core.theme.disabledColor

@Composable
fun CollectionTopAppBar(
    modifier: Modifier = Modifier,
    buttons: List<TopBarButtonConfig>,
    buttonClick: (buttonId: String) -> Unit,
) {
    TopAppBar(
        modifier = modifier.padding(16.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        TopBarContentButtons(
            modifier = Modifier,
            buttons = buttons,
            buttonClick = buttonClick
        )
    }
}

@Composable
fun TopBarContentButtons(
    modifier: Modifier = Modifier,
    buttons: List<TopBarButtonConfig> = emptyList(),
    buttonClick: (buttonId: String) -> Unit,
) {
    Row(
        modifier
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        buttons.forEach { config ->
            TopBarButton(
                config = config,
                onClick = buttonClick
            )
        }
    }
}

@Composable
fun TopBarButton(
    modifier: Modifier = Modifier,
    config: TopBarButtonConfig,
    onClick: (buttonId: String) -> Unit
) {
    IconButton(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.Transparent),
        onClick = { onClick(config.id) },
    ) {
        Icon(
            modifier = Modifier.size(config.iconSize),
            painter = config.iconPainter,
            contentDescription = config.contentDescription,
            tint = disabledColor
        )
    }
}

class TopBarButtonConfig(
    val id: String,
    val iconPainter: Painter,
    val contentDescription: String = id,
    val iconSize: Dp = 24.dp,
)