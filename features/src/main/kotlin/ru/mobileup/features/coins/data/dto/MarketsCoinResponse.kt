package ru.mobileup.features.coins.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.mobileup.features.coins.domain.CoinMarket
import ru.mobileup.features.coins.domain.MarketId

@Serializable
data class MarketsCoinResponse(
    @SerialName("name") val name: String,
    @SerialName("price") val price: String,
    @SerialName("base") val base: String,
    @SerialName("quote") val quote: String,
)

fun MarketsCoinResponse.toDomain(): CoinMarket {
    return CoinMarket(
        id = MarketId(getMarketId()),
        name = name,
        price = formatPrice(price),
    )
}