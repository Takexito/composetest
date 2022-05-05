package ru.mobileup.features.coins.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinDetails
import ru.mobileup.features.coins.domain.CoinId

@Serializable
data class DetailedCoinResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("rank") val rank: Int,
    @SerialName("price_usd") val price: String,
)

fun DetailedCoinResponse.toDomain(): CoinDetails {
    return CoinDetails(
        id = CoinId(id),
        name = name,
        rank = rank,
        price = formatPrice(price),
        markets = listOf()
    )
}