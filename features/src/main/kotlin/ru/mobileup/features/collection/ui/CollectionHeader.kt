package ru.mobileup.features.collection.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mobileup.core.theme.CollectionShape
import ru.mobileup.core.theme.fourthColor
import ru.mobileup.core.theme.textColor
import ru.mobileup.features.collection.domain.Statistic

typealias HeaderStatistic = Statistic

@Composable
fun CollectionHeader(
    modifier: Modifier = Modifier,
    shape: Shape = CollectionShape.medium,
    backgroundColor: Color = Color.Transparent,
    border: BorderStroke = BorderStroke(2.dp, Color.Gray.copy(alpha = 0.1f)),
    separateBoarder: BorderStroke = BorderStroke(
        1.dp,
        Color.Gray.copy(alpha = 0.1f)
    ), // TODO: rename
    contentPaddings: PaddingValues = PaddingValues(vertical = 16.dp),
    statistics: List<HeaderStatistic>,
    onItemClick: (pos: Int) -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        border = border
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            statistics.forEachIndexed { index, statistic ->
                CollectionHeaderItem(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    iconConfig = CollectionHeaderItemIconConfig(
                        iconPainter = painterResource(id = statistic.type.getIconId()),
                    ),
                    textConfig = CollectionHeaderItemTextConfig(text = statistic.value.toString()),
                    backgroundColor = backgroundColor,
                    border = separateBoarder,
                    contentPaddings = contentPaddings,
                    spaceBetweenIconAndText = 16.dp,
                    onClick = { onItemClick(index) },
                )
            }
        }
    }
}

@Composable
fun CollectionHeaderItem(
    modifier: Modifier,
    backgroundColor: Color,
    border: BorderStroke,
    contentPaddings: PaddingValues,
    spaceBetweenIconAndText: Dp,
    textConfig: CollectionHeaderItemTextConfig,
    iconConfig: CollectionHeaderItemIconConfig,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .clickable { onClick() },
        color = backgroundColor,
        border = border
    ) {
        Column(
            modifier = Modifier.padding(contentPaddings),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .height(iconConfig.iconSize)
                    .fillMaxWidth(),
                painter = iconConfig.iconPainter,
                contentDescription = iconConfig.contentDescription,
                tint = fourthColor
            )
            Spacer(
                modifier = Modifier
                    .height(spaceBetweenIconAndText)
                    .fillMaxWidth()
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = textConfig.text,
                textAlign = TextAlign.Center,
                fontSize = textConfig.fontSize,
                color = textColor
            )
        }
    }
}

class CollectionHeaderItemTextConfig(
    val text: String,
    val fontSize: TextUnit = 20.sp,
)

class CollectionHeaderItemIconConfig(
    val iconPainter: Painter,
    val iconSize: Dp = 32.dp,
    val contentDescription: String = "",
)