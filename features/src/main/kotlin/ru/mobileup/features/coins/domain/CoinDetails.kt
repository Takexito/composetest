package ru.mobileup.features.coins.domain

data class CoinDetails(
    val id: CoinId,
    val name: String,
    val rank: Int,
    val price: Double,
    val markets: List<CoinMarket>
)

fun CoinDetails.newCoinDetails(
    id: CoinId = this.id,
    name: String = this.name,
    rank: Int = this.rank,
    markets: List<CoinMarket> = this.markets
): CoinDetails {
    return CoinDetails(
        id = id,
        name = name,
        rank = rank,
        price = price,
        markets = markets
    )
}