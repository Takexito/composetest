package ru.mobileup.features.coins.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinId

@Serializable
data class CoinResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("price_usd") val price: String
)

fun CoinResponse.toDomain(): Coin {
    return Coin(
        id = CoinId(id),
        name = name,
        symbol = symbol,
        price = formatPrice(price)
    )
}