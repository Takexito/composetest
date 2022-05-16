package ru.mobileup.features.collection.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mobileup.core.R
import ru.mobileup.core.theme.CollectionShape

typealias CollectionItemType = Any

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionCardList(
    modifier: Modifier = Modifier,
    cardOverlapSize: Dp = (-64).dp,
    collections: List<CollectionItemType>,
    onItemClick: (item: CollectionItemType, index: Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(cardOverlapSize)
    ) {
        collections.forEachIndexed { index, collection ->
            CollectionCardItem(
                modifier = Modifier
                    .fillMaxWidth(),
                cardConfig = CollectionCardItemCardConfig(
                    backgroundPainter = painterResource(id = R.drawable.ic_launcher_background)
                ),
                titleConfig = CollectionCardItemTitleConfig(
                    title = "Title $index",
                ),
                onClick = { onItemClick(collection, index) }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CollectionCardItem(
    modifier: Modifier,
    cardConfig: CollectionCardItemCardConfig = CollectionCardItemCardConfig(
        painterResource(
            id = R.drawable.ic_launcher_background
        )
    ),
    titleConfig: CollectionCardItemTitleConfig = CollectionCardItemTitleConfig(title = "Animal"),
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(cardConfig.aspectRatio),
        onClick = onClick,
        shape = cardConfig.shape,
        border = cardConfig.border,
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = titleConfig.titleAlignment,
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = cardConfig.backgroundPainter,
                contentDescription = cardConfig.contentDescription
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(titleConfig.titlePaddingValues),
                fontSize = titleConfig.titleFontSize,
                text = titleConfig.title
            )
        }
    }
}

class CollectionCardItemTitleConfig(
    val title: String,
    val titleAlignment: Alignment = Alignment.TopStart,
    val titlePaddingValues: PaddingValues = PaddingValues(start = 32.dp, top = 32.dp),
    val titleFontSize: TextUnit = 24.sp
)

class CollectionCardItemCardConfig(
    val backgroundPainter: Painter,
    val contentDescription: String = "",
    val aspectRatio: Float = 3f / 2f,
    val shape: Shape = CollectionShape.medium,
    val border: BorderStroke = BorderStroke(1.dp, Color.Gray),
)