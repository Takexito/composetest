package ru.mobileup.features.collection.ui.card

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mobileup.core.theme.CollectionShape
import ru.mobileup.core.theme.ProgressShape
import java.lang.Float.min

@Preview
@Composable
fun CardUiTest() {
    CardUi()
}

@Composable
fun CardUi() {
    Progress(color = Color.Black, data = ProgressData(1, 1))
}

@Composable
fun Progress(
    paddings: PaddingValues = PaddingValues(8.dp),
    color: Color,
    data: ProgressData
) {
    Surface(
        modifier = Modifier
            .padding(paddings)
            .fillMaxSize(),
        color = Color.Transparent,
        shape = ProgressShape.medium
    ) {
        Column(
            modifier = Modifier.background(Color.Transparent)
        ) {
//            Text(text = data.currValue.toString(), color = color)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = data.maxValue.toString())
            RoundProgress(
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(height = 200.dp, width = 100.dp),
                percent = 99f,
                shape = ProgressShape.medium,
                color = Color.Green,
                strokeWidth = 20f,
                startPoint = 0.5f
            )
        }
    }
}

data class ProgressData(
    val maxValue: Int,
    val currValue: Int
)
