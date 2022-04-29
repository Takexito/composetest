package ru.mobileup.features.coins.domain

data class CoinDetails(
    val id: CoinId,
    val name: String,
    val rank: Int,
    val priceUsd: Double,
    val markets: List<CoinMarket>
)