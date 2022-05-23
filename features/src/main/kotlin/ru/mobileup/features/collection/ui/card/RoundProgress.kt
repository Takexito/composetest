package ru.mobileup.features.collection.ui.card

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath


@Composable
fun RoundProgress(
    modifier: Modifier = Modifier,
    percent: Float,
    shape: Shape,
    color: Color,
    strokeWidth: Float,
    startPoint: Float
) {
    Canvas(modifier = modifier
        .graphicsLayer(alpha = 0.99f),
        onDraw = {

            val outline = shape.createOutline(size, layoutDirection, this)
            val rect = (outline as Outline.Rounded).roundRect
            val path = Path()
            path.addOutline(outline)

            val segmentConfig = SegmentConfig(
                startPoint = startPoint,
                percent = percent,
                rect = rect,
                strokeWidth = strokeWidth,
                color = color,
            )
            clipPath(path) {
                if (percent > 0f) {
                    drawSegment(
                        segmentConfig,
                        SegmentConfig.SizesConfig(
                            endSegmentPercent = 25f,
                            startSegmentPercent = 0f,
                            cornerRadius = rect.topLeftCornerRadius,
                            segment = Segment.Top
                        )
                    )
                }
                if (percent > 25f) {
                    drawSegment(
                        segmentConfig,
                        SegmentConfig.SizesConfig(
                            endSegmentPercent = 50f,
                            startSegmentPercent = 25f,
                            cornerRadius = rect.bottomRightCornerRadius,
                            segment = Segment.Right
                        )
                    )
                }
                if (percent > 50f) {
                    drawSegment(
                        segmentConfig,
                        SegmentConfig.SizesConfig(
                            endSegmentPercent = 75f,
                            startSegmentPercent = 50f,
                            cornerRadius = rect.bottomLeftCornerRadius,
                            segment = Segment.Bottom
                        )
                    )
                }
                if (percent > 75f) {
                    drawSegment(
                        segmentConfig,
                        SegmentConfig.SizesConfig(
                            endSegmentPercent = 100f,
                            startSegmentPercent = 75f,
                            cornerRadius = rect.topLeftCornerRadius,
                            segment = Segment.Left
                        )
                    )
                }
            }
        }
    )
}

private fun DrawScope.drawSegment(
    segmentConfig: SegmentConfig,
    sizesConfig: SegmentConfig.SizesConfig
) {
    val sizeMultiplier = (1 - segmentConfig.startPoint)
    val deltaSegmentPercent = (sizesConfig.endSegmentPercent - sizesConfig.startSegmentPercent)
    val maxPercentFirstSide = deltaSegmentPercent * sizeMultiplier
    val startSecondSidePercent = sizesConfig.startSegmentPercent + maxPercentFirstSide
    val perPercentY = (segmentConfig.rect.height * sizeMultiplier) / maxPercentFirstSide
    val perPercentX = (segmentConfig.rect.width * sizeMultiplier) / (maxPercentFirstSide)
    val localPercentSecondSide =
        java.lang.Float.min(
            sizesConfig.endSegmentPercent,
            segmentConfig.percent
        ) - startSecondSidePercent
    val percentRadius = sizesConfig.cornerRadius.x / perPercentY
    val localPercentFirstSide =
        java.lang.Float.min(
            maxPercentFirstSide + percentRadius,
            segmentConfig.percent - sizesConfig.startSegmentPercent
        )
    val deltaFirstSidePercent = (maxPercentFirstSide) - localPercentFirstSide

    val finishHorizontalLineConfig =
        FinishLineConfig(
            segmentConfig.rect,
            segmentConfig.strokeWidth,
            perPercentX,
            localPercentFirstSide,
            segmentConfig.color,
            segmentConfig.startPoint
        )
    val finishVerticalLineConfig =
        FinishLineConfig(
            segmentConfig.rect,
            segmentConfig.strokeWidth,
            perPercentY,
            localPercentFirstSide,
            segmentConfig.color,
            segmentConfig.startPoint
        )

    val startHorizontalLineConfig =
        StartLineConfig(
            segmentConfig.rect,
            segmentConfig.strokeWidth,
            perPercentX,
            localPercentSecondSide,
            segmentConfig.color
        )
    val startVerticalLineConfig =
        StartLineConfig(
            segmentConfig.rect,
            segmentConfig.strokeWidth,
            perPercentY,
            localPercentSecondSide,
            segmentConfig.color
        )

    val roundCornerConfig =
        RoundCornerConfig(
            percentRadius,
            deltaFirstSidePercent,
            segmentConfig.strokeWidth,
            sizesConfig.cornerRadius,
            segmentConfig.color
        )

    when (sizesConfig.segment) {
        Segment.Top -> drawTopFinishLine(finishHorizontalLineConfig)
        Segment.Right -> drawRightFinishLine(finishVerticalLineConfig)
        Segment.Bottom -> drawBottomFinishLine(finishHorizontalLineConfig)
        Segment.Left -> drawLeftFinishLine(finishVerticalLineConfig)
    }

    if (segmentConfig.percent >= startSecondSidePercent) {
        when (sizesConfig.segment) {
            Segment.Top -> drawRightStartLine(startVerticalLineConfig)
            Segment.Right -> drawBottomStartLine(startHorizontalLineConfig)
            Segment.Bottom -> drawLeftStartLine(startVerticalLineConfig)
            Segment.Left -> drawTopStartLine(startHorizontalLineConfig)
        }
    }

    if (deltaFirstSidePercent <= percentRadius) {
        when (sizesConfig.segment) {
            Segment.Top -> {
                val offsetX = segmentConfig.rect.width
                val offsetY = 0f
                drawRoundCorner(
                    roundCornerConfig,
                    RoundCornerConfig.ArcConfig(startAngle = -90f, offsetX, offsetY)
                )
            }
            Segment.Right -> {
                val offsetX = segmentConfig.rect.width
                val offsetY = segmentConfig.rect.height
                drawRoundCorner(
                    roundCornerConfig,
                    RoundCornerConfig.ArcConfig(startAngle = 0f, offsetX, offsetY)
                )
            }
            Segment.Bottom -> {
                val offsetX = 0f
                val offsetY = segmentConfig.rect.height
                drawRoundCorner(
                    roundCornerConfig,
                    RoundCornerConfig.ArcConfig(startAngle = 90f, offsetX, offsetY)
                )
            }
            Segment.Left -> {
                val offsetX = 0f
                val offsetY = 0f
                drawRoundCorner(
                    roundCornerConfig,
                    RoundCornerConfig.ArcConfig(startAngle = 180f, offsetX, offsetY)
                )
            }
        }
    }
}

private fun DrawScope.drawTopStartLine(
    config: StartLineConfig
) {
    val xStart = config.rect.left
    val yStart = config.rect.top + config.strokeWidth / 2
    val xEnd = xStart + config.perPercent * config.localPercent
    val yEnd = yStart

    drawLine(
        color = config.color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = config.strokeWidth,
    )
}

private fun DrawScope.drawLeftFinishLine(
    config: FinishLineConfig
) {
    val xStart = config.rect.left + config.strokeWidth / 2
    val yStart = config.rect.bottom * config.startPoint
    val xEnd = xStart
    val yEnd = yStart - config.perPercent * config.localPercent

    drawLine(
        color = config.color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = config.strokeWidth,
    )
}

private fun DrawScope.drawBottomFinishLine(
    config: FinishLineConfig
) {
    val xStart = config.rect.width * config.startPoint
    val yStart = config.rect.bottom - config.strokeWidth / 2
    val xEnd = xStart - config.perPercent * config.localPercent
    val yEnd = yStart

    drawLine(
        color = config.color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = config.strokeWidth,
    )
}

private fun DrawScope.drawLeftStartLine(
    config: StartLineConfig
) {
    val xStart = config.rect.left + config.strokeWidth / 2
    val yStart = config.rect.bottom
    val xEnd = xStart
    val yEnd = yStart - config.perPercent * config.localPercent

    drawLine(
        color = config.color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = config.strokeWidth,
    )
}

private fun DrawScope.drawRightFinishLine(
    config: FinishLineConfig
) {
    val xStart = config.rect.right - config.strokeWidth / 2
    val yStart = config.rect.height * config.startPoint
    val xEnd = xStart
    val yEnd = yStart + config.perPercent * config.localPercent

    drawLine(
        color = config.color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = config.strokeWidth,
    )
}

private fun DrawScope.drawBottomStartLine(
    config: StartLineConfig
) {
    val xStart = config.rect.right
    val yStart = config.rect.bottom - config.strokeWidth / 2
    val xEnd = xStart - config.perPercent * config.localPercent
    val yEnd = yStart

    drawLine(
        color = config.color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = config.strokeWidth,
    )
}

private fun DrawScope.drawRightStartLine(
    config: StartLineConfig
) {
    val xStart = config.rect.right - config.strokeWidth / 2
    val yStart = config.rect.top
    val xEnd = xStart
    val yEnd = yStart + config.perPercent * config.localPercent

    drawLine(
        color = config.color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = config.strokeWidth,
    )
}

private fun DrawScope.drawTopFinishLine(
    config: FinishLineConfig,
) {
    val yStart = config.rect.top + config.strokeWidth / 2
    val xStart = config.rect.width * config.startPoint
    val xEnd = xStart + config.perPercent * config.localPercent
    val yEnd = yStart
    drawLine(
        color = config.color,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = config.strokeWidth,
    )
}

private fun DrawScope.drawRoundCorner(
    config: RoundCornerConfig,
    arcConfig: RoundCornerConfig.ArcConfig
) {
    val maxSweepAngle = 90f
    val sweepAngle = minOf(
        (config.percentRadius - config.deltaPercent) / config.percentRadius * (maxSweepAngle / 2),
        maxSweepAngle
    )
    val delta = config.strokeWidth

    val a = delta * 3 + config.cornerRadius.x
    val b = delta + config.cornerRadius.x

    val offsetX = maxOf(arcConfig.offsetX - a, 0f)
    val offsetY = maxOf(arcConfig.offsetY - a, 0f)

    drawArc(
        color = config.color,
        startAngle = arcConfig.startAngle,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(a, a),
        topLeft = Offset(offsetX, offsetY),
    )

    drawArc(
        color = Color.Black,
        startAngle = arcConfig.startAngle,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(b, b),
        topLeft = Offset(offsetX + delta, offsetY + delta),
        blendMode = BlendMode.SrcOut
    )
}

class SegmentConfig(
    val startPoint: Float,
    val percent: Float,
    val rect: RoundRect,
    val strokeWidth: Float,
    val color: Color,
) {
    class SizesConfig(
        val segment: Segment,
        val endSegmentPercent: Float,
        val startSegmentPercent: Float,
        val cornerRadius: CornerRadius,
    )
}

class RoundCornerConfig(
    val percentRadius: Float,
    val deltaPercent: Float,
    val strokeWidth: Float,
    val cornerRadius: CornerRadius,
    val color: Color
) {
    class ArcConfig(
        val startAngle: Float,
        val offsetX: Float,
        val offsetY: Float,
    )
}

class StartLineConfig(
    val rect: RoundRect,
    val strokeWidth: Float,
    val perPercent: Float,
    val localPercent: Float,
    val color: Color,
)

class FinishLineConfig(
    val rect: RoundRect,
    val strokeWidth: Float,
    val perPercent: Float,
    val localPercent: Float,
    val color: Color,
    val startPoint: Float,
)

enum class Segment {
    Top, Right, Bottom, Left
}
