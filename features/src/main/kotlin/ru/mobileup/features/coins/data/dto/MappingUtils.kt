package ru.mobileup.features.coins.data.dto

import ru.mobileup.core.error_handling.DeserializationException
import java.math.RoundingMode
import java.text.DecimalFormat

fun formatPrice(price: String, formatPattern: String = "#.##"): Double {
    return try {
        val df = DecimalFormat(formatPattern)
        df.roundingMode = RoundingMode.DOWN
        val format = df.format(price.toDouble())
        format.toDouble()
    } catch (e: Exception) {
        throw DeserializationException(e)
    }
}

fun MarketsCoinResponse.getMarketId(): String {
    return "$name-$base-$quote"
}