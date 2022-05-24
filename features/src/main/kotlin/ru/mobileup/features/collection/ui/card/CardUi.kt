package ru.mobileup.features.collection.ui.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mobileup.core.theme.*
import kotlin.random.Random

@Preview
@Composable
fun CardUiTest() {
    val progressData: List<ProgressData> = listOf(
        ProgressData(1, 1, Card.Type.BLUE),
        ProgressData(10, 2, Card.Type.RED),
        ProgressData(100, 1, Card.Type.GREEN),
        ProgressData(10, 5, Card.Type.MAGENTA),
        ProgressData(10, 3, Card.Type.YELLOW),
    )
    val cards: List<Card> = buildList {
        repeat(30) {
            add(
                Card(
                    it,
                    Card.Type.values()[(0 until Card.Type.values().size).random()],
                    "",
                    Random.nextBoolean()
                )
            )
        }
    }
    CardUi(progressData, cards)
}

@Composable
fun CardUi(progressData: List<ProgressData>, cards: List<Card>) {
    Column(
        Modifier
            .background(backgroundColor)
            .padding(horizontal = 24.dp)
    ) {
        CardGrid(cards = cards, progressData = progressData)
    }
}

@Composable
fun HeaderCardUi(progressData: List<ProgressData>) {
    Column {
        HeaderText(modifier = Modifier.padding(vertical = 16.dp))
        ProgressRow(data = progressData)
    }
}

@Composable
fun HeaderText(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Cards",
            fontWeight = FontWeight(1000),
            fontSize = 48.sp,
            color = textColor,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Use seeds to collect cards. Rumour has it that something special happens when you collect them all!",
            fontSize = 16.sp,
            color = captionColor,
            fontWeight = FontWeight(500),
            lineHeight = 24.sp,
        )
    }
}

@Composable
fun CardGrid(cards: List<Card>, progressData: List<ProgressData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        header {
            HeaderCardUi(progressData)
        }
        items(
            count = cards.size,
            key = { index -> cards[index].id }
        ) { index ->
            CardItem(card = cards[index], PaddingValues(8.dp))
        }
    }
}


@Composable
fun CardItem(card: Card, paddings: PaddingValues = PaddingValues()) {
    val modifier = Modifier
        .padding(paddings)
        .aspectRatio(1f)

    if (card.isOpen) OpenCardItem(modifier, card)
    else CloseCardItem(modifier, card)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OpenCardItem(modifier: Modifier, card: Card) {
    Card(
        shape = CardShape.medium,
        modifier = modifier,
        border = BorderStroke(2.dp, card.type.getColor()),
        backgroundColor = Color.Transparent,
        onClick = { /*TODO*/ }) {

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CloseCardItem(modifier: Modifier, card: Card) {
    Card(
        shape = CardShape.medium,
        modifier = modifier,
        backgroundColor = Color.Transparent,
        border = BorderStroke(1.dp, disabledColor),
        onClick = { }
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = card.id.toString(),
                color = captionColor,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            )
        }
    }
}

@Composable
fun ProgressRow(
    paddings: PaddingValues = PaddingValues(8.dp),
    data: List<ProgressData>
) {
    Row(
        modifier = Modifier
            .padding(paddings)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        data.forEach { data ->
            ProgressItem(data = data)
        }
    }
}

@Composable
fun ProgressItem(
    data: ProgressData
) {
    val size = DpSize(55.dp, 80.dp)
    val color = data.type.getColor()

    RoundProgressBox(
        modifier = Modifier.size(size),
        boxModifier = Modifier.size(size),
        percent = data.percent * 100f,
        shape = ProgressShape.medium,
        color = color,
        strokeWidth = 14f,
        startPoint = 0.5f
    ) {
        Column(
            modifier = Modifier.size(size),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = data.currValue.toString(),
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight(1000)
            )
            Text(
                text = "----",
                color = captionColor,
                fontSize = 12.sp,
            )
            Text(
                text = data.maxValue.toString(),
                color = captionColor,
                fontSize = 14.sp,
                fontWeight = FontWeight(500)
            )
        }
    }
}

data class ProgressData(
    val maxValue: Int,
    val currValue: Int,
    val type: Card.Type,
) {
    val percent: Float = currValue.toFloat() / maxValue.toFloat()
}

fun Modifier.transparent() = this.then(background(Color.Transparent))

data class Card(
    val id: Int,
    val type: Type,
    val imageSrc: String,
    val isOpen: Boolean
) {
    enum class Type {
        RED, GREEN, BLUE, MAGENTA, YELLOW;

        fun getColor(): Color {
            return when (this) {
                RED -> primaryColor
                GREEN -> secondColor
                BLUE -> thirdColor
                MAGENTA -> fourthColor
                YELLOW -> fifthColor
            }
        }
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}