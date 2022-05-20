package ru.mobileup.features.collection.ui.card

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
            RoundRect(
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(height = 200.dp, width = 100.dp),
                percent = 50f
            )
        }
    }
}

@Composable
fun RoundRect(modifier: Modifier = Modifier, percent: Float) {
    Canvas(modifier = modifier
        .graphicsLayer(alpha = 0.99f),
        onDraw = {
            val outline = CollectionShape.medium.createOutline(size, layoutDirection, this)

            val color = Color.Red
            val strokeWidth = 20f

            val rect = (outline as Outline.Rounded).roundRect
            val path = Path()
            path.addOutline(outline)

            val mult = 1f/2f

            clipPath(path) {
                val anchorX = rect.width * mult
                val anchorY = rect.height * mult
                if (percent > 0f) {
                    val startPercent = 0f
                    val allMaxPercent = 25f
                    val maxPercent = (allMaxPercent - startPercent) * mult
                    val startSecond = startPercent + maxPercent

                    val perPercent1 = anchorX / maxPercent
                    val cornerRadius = rect.topLeftCornerRadius
                    val percentRadius = cornerRadius.x / perPercent1
                    val localPercent = min(maxPercent + percentRadius, percent) - startPercent
                    val deltaPercent = (maxPercent) - localPercent

                    drawTopRightLine(rect, strokeWidth, perPercent1, localPercent, color, mult)
                    if (percent >= startSecond + percentRadius) {
                        val perPercent2 = (anchorY) / (maxPercent)
                        val localPercent2 = min(allMaxPercent, percent) - startSecond
                        drawRightTopLine(
                            rect,
                            strokeWidth,
                            perPercent2,
                            localPercent2,
                            color
                        )
                    }
                    if (deltaPercent <= percentRadius) {
                        drawTopRightRoundCorner(
                            percentRadius,
                            deltaPercent,
                            strokeWidth,
                            cornerRadius,
                            rect
                        )
                    }
                }

                if (percent > 25f) {
                    drawSegment(
                        allMaxPercent = 50f,
                        startPercent = 25f,
                        mult = mult,
                        anchorY = anchorY,
                        anchorX = anchorX,
                        cornerRadius = rect.bottomRightCornerRadius,
                        percent = percent,
                        rect = rect,
                        strokeWidth = strokeWidth,
                        color = color
                    )
                }

                if (percent > 50f) {
                    val startPercent = 50f
                    val allMaxPercent = 75f
                    val maxPercent = (allMaxPercent - startPercent) * mult
                    val startSecond = startPercent + maxPercent

                    val perPercent1 = anchorX / maxPercent
                    val cornerRadius = rect.bottomLeftCornerRadius
                    val percentRadius = cornerRadius.x / perPercent1
                    val localPercent =
                        min(maxPercent + startPercent + percentRadius, percent) - startPercent
                    val deltaPercent = maxPercent - localPercent

                    drawBottomLeftLine(rect, strokeWidth, perPercent1, localPercent, color, mult)
                    if (percent >= startSecond + percentRadius) {
                        val perPercent2 = anchorY / maxPercent
                        val localPercent2 = min(allMaxPercent, percent) - startSecond
                        drawLeftBottomLine(
                            rect,
                            strokeWidth,
                            perPercent2,
                            localPercent2,
                            color
                        )
                    }
                    if (deltaPercent <= percentRadius) {
                        drawBottomLeftRoundCorner(
                            percentRadius,
                            deltaPercent,
                            strokeWidth,
                            cornerRadius,
                            rect
                        )
                    }
                }

                if (percent > 75f) {
                    val startPercent = 75f
                    val allMaxPercent = 100f
                    val maxPercent = (allMaxPercent - startPercent) * mult
                    val startSecond = startPercent + maxPercent

                    val perPercent1 = anchorY / maxPercent
                    val cornerRadius = rect.bottomLeftCornerRadius
                    val percentRadius = cornerRadius.x / perPercent1
                    val localPercent =
                        min(maxPercent + startPercent + percentRadius, percent) - startPercent
                    val deltaPercent = maxPercent - localPercent

                    drawLeftTopLine(rect, strokeWidth, perPercent1, localPercent, color, mult)
                    if (percent >= startSecond + percentRadius) {
                        val perPercent2 = anchorX / maxPercent
                        val localPercent2 = min(allMaxPercent, percent) - startSecond
                        drawTopLeftLine(
                            rect,
                            strokeWidth,
                            perPercent2,
                            localPercent2,
                            color
                        )
                    }
                    if (deltaPercent <= percentRadius) {
                        drawTopLeftRoundCorner(
                            percentRadius,
                            deltaPercent,
                            strokeWidth,
                            cornerRadius,
                            rect
                        )
                    }
                }
            }
        })
}

private fun DrawScope.drawSegment(
    allMaxPercent: Float,
    startPercent: Float,
    mult: Float,
    anchorY: Float,
    anchorX: Float,
    cornerRadius: CornerRadius,
    percent: Float,
    rect: RoundRect,
    strokeWidth: Float,
    color: Color,
) {
    val maxPercent = (allMaxPercent - startPercent) * mult
    val startSecond = startPercent + maxPercent
    val perPercent = anchorY / maxPercent
    val percentRadius = cornerRadius.x / perPercent
    val localPercent =
        min(maxPercent + startPercent + percentRadius, percent) - startPercent
    val deltaPercent = (maxPercent) - localPercent

    drawRightBottomLine(rect, strokeWidth, perPercent, localPercent, color, mult)
    if (percent >= startSecond + percentRadius) {
        val perPercent2 = anchorX / (maxPercent)
        val localPercent2 = min(allMaxPercent, percent) - startSecond
        drawBottomRightLine(
            rect,
            strokeWidth,
            perPercent2,
            localPercent2,
            color
        )
    }
    if (deltaPercent <= percentRadius) {
        drawBottomRightRoundCorner(
            percentRadius,
            deltaPercent,
            strokeWidth,
            cornerRadius,
            rect
        )
    }
}

private fun DrawScope.drawTopLeftLine(
    rect: RoundRect,
    strokeWidth: Float,
    perPercent: Float,
    localPercent: Float,
    color: Color
) {
    val xStart2 = rect.left
    val yStart2 = rect.top + strokeWidth / 2
    val xEnd2 = xStart2 + perPercent * localPercent
    val yEnd2 = yStart2

    drawLine(
        color = color,
        start = Offset(xStart2, yStart2),
        end = Offset(xEnd2, yEnd2),
        strokeWidth = strokeWidth,
    )
}

private fun DrawScope.drawLeftTopLine(
    rect: RoundRect,
    strokeWidth: Float,
    perPercent: Float,
    localPercent: Float,
    color: Color,
    mult: Float
) {
    val xStart = rect.left + strokeWidth / 2
    val yStart = rect.bottom * (1 - mult)
    val xEnd = xStart
    val yEnd = yStart - perPercent * localPercent

    drawLine(
        color = color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = strokeWidth,
    )
}

private fun DrawScope.drawTopLeftRoundCorner(
    percentRadius: Float,
    deltaPercent: Float,
    strokeWidth: Float,
    cornerRadius: CornerRadius,
    rect: RoundRect
) {
    val maxSweepAngle = 90f
    val sweepAngle = minOf(
        (percentRadius - deltaPercent) / percentRadius * (maxSweepAngle / 2),
        maxSweepAngle
    )
    val delta = strokeWidth

    val a = delta * 3 + cornerRadius.x
    val b = delta + cornerRadius.x

    val offsetX = 0f
    val offsetY = 0f

    drawArc(
        color = Color.Red,
        startAngle = 180f,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(a, a),
        topLeft = Offset(offsetX, offsetY)
    )
    drawArc(
        color = Color.Transparent,
        startAngle = 180f,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(b, b),
        topLeft = Offset(offsetX + delta, offsetY + delta),
        blendMode = BlendMode.Clear
    )
}

private fun DrawScope.drawBottomLeftLine(
    rect: RoundRect,
    strokeWidth: Float,
    perPercent: Float,
    localPercent: Float,
    color: Color,
    mult: Float
) {
    val xStart2 = rect.width * (1 - mult)
    val yStart2 = rect.bottom - strokeWidth / 2
    val xEnd2 = xStart2 - perPercent * localPercent
    val yEnd2 = yStart2

    drawLine(
        color = color,
        start = Offset(xStart2, yStart2),
        end = Offset(xEnd2, yEnd2),
        strokeWidth = strokeWidth,
    )
}

private fun DrawScope.drawLeftBottomLine(
    rect: RoundRect,
    strokeWidth: Float,
    perPercent: Float,
    localPercent: Float,
    color: Color
) {
    val xStart = rect.left + strokeWidth / 2
    val yStart = rect.bottom
    val xEnd = xStart
    val yEnd = yStart - perPercent * localPercent

    drawLine(
        color = color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = strokeWidth,
    )
}

private fun DrawScope.drawBottomLeftRoundCorner(
    percentRadius: Float,
    deltaPercent: Float,
    strokeWidth: Float,
    cornerRadius: CornerRadius,
    rect: RoundRect
) {
    val maxSweepAngle = 90f
    val sweepAngle = minOf(
        (percentRadius - deltaPercent) / percentRadius * (maxSweepAngle / 2),
        maxSweepAngle
    )
    val delta = strokeWidth

    val a = delta * 3 + cornerRadius.x
    val b = delta + cornerRadius.x

    val offsetX = 0f
    val offsetY = rect.height - a

    drawArc(
        color = Color.Red,
        startAngle = 90f,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(a, a),
        topLeft = Offset(offsetX, offsetY)
    )
    drawArc(
        color = Color.Transparent,
        startAngle = 90f,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(b, b),
        topLeft = Offset(offsetX + delta, offsetY + delta),
        blendMode = BlendMode.Clear
    )
}

private fun DrawScope.drawRightBottomLine(
    rect: RoundRect,
    strokeWidth: Float,
    perPercent: Float,
    localPercent: Float,
    color: Color,
    mult: Float

) {
    val xStart = rect.right - strokeWidth / 2
    val yStart = rect.height * (1 - mult)
    val xEnd = xStart
    val yEnd = yStart + perPercent * localPercent

    drawLine(
        color = color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = strokeWidth,
    )
}

private fun DrawScope.drawBottomRightLine(
    rect: RoundRect,
    strokeWidth: Float,
    perPercent: Float,
    localPercent: Float,
    color: Color
) {
    val xStart2 = rect.right
    val yStart2 = rect.bottom - strokeWidth / 2
    val xEnd2 = xStart2 - perPercent * localPercent
    val yEnd2 = yStart2

    drawLine(
        color = color,
        start = Offset(xStart2, yStart2),
        end = Offset(xEnd2, yEnd2),
        strokeWidth = strokeWidth,
    )
}

private fun DrawScope.drawBottomRightRoundCorner(
    percentRadius: Float,
    deltaPercent: Float,
    strokeWidth: Float,
    cornerRadius: CornerRadius,
    rect: RoundRect,
) {
    val maxSweepAngle = 90f
    val sweepAngle = minOf(
        (percentRadius - deltaPercent) / percentRadius * (maxSweepAngle / 2),
        maxSweepAngle
    )
    val delta = strokeWidth

    val a = delta * 3 + cornerRadius.x
    val b = delta + cornerRadius.x

    val offsetX = rect.width - a
    val offsetY = rect.height - a

    drawArc(
        color = Color.Red,
        startAngle = 0f,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(a, a),
        topLeft = Offset(offsetX, offsetY)
    )
    drawArc(
        color = Color.Transparent,
        startAngle = 0f,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(b, b),
        topLeft = Offset(offsetX + delta, offsetY + delta),
        blendMode = BlendMode.Clear
    )
}

private fun DrawScope.drawRightTopLine(
    rect: RoundRect,
    strokeWidth: Float,
    perPercent: Float,
    localPercent: Float,
    color: Color
) {
    val xStart2 = rect.right - strokeWidth / 2
    val yStart2 = rect.top
    val xEnd2 = xStart2
    val yEnd2 = yStart2 + perPercent * localPercent

    drawLine(
        color = color,
        start = Offset(xStart2, yStart2),
        end = Offset(xEnd2, yEnd2),
        strokeWidth = strokeWidth,
    )
}

private fun DrawScope.drawTopRightLine(
    rect: RoundRect,
    strokeWidth: Float,
    perPercent1: Float,
    localPercent: Float,
    color: Color,
    mult: Float

) {
    val yStart = rect.top + strokeWidth / 2
    val xStart = rect.width * (1 - mult)
    val xEnd = xStart + perPercent1 * localPercent
    val yEnd = yStart
    drawLine(
        color = color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = strokeWidth,
    )
}

private fun DrawScope.drawTopRightRoundCorner(
    percentRadius: Float,
    deltaPercent: Float,
    strokeWidth: Float,
    cornerRadius: CornerRadius,
    rect: RoundRect
) {
    val maxSweepAngle = 90f
    val sweepAngle = minOf(
        (percentRadius - deltaPercent) / percentRadius * (maxSweepAngle / 2),
        maxSweepAngle
    )
    val delta = strokeWidth

    val a = delta * 3 + cornerRadius.x
    val b = delta + cornerRadius.x

    val offsetX = rect.width - a
    val offsetY = 0f

    drawArc(
        color = Color.Red,
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(a, a),
        topLeft = Offset(offsetX, 0f),
    )

    drawArc(
        color = Color.Black,
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(b, b),
        topLeft = Offset(offsetX + delta, offsetY + delta),
        blendMode = BlendMode.SrcOut
    )

}

data class ProgressData(
    val maxValue: Int,
    val currValue: Int
)
