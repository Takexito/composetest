package ru.mobileup.features.coins.data

import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinDetails
import ru.mobileup.features.coins.domain.CoinId
import ru.mobileup.features.coins.domain.CoinMarket


interface CoinRepository{
    fun coinList(): List<Coin>
    fun coinDetailed(coinId: CoinId): CoinDetails
    fun coinMarkets(): CoinMarket

    class Fake: CoinRepository{
        override fun coinList(): List<Coin> {
            TODO("Not yet implemented")
        }

        override fun coinDetailed(coinId: CoinId): CoinDetails {
            TODO("Not yet implemented")
        }

        override fun coinMarkets(): CoinMarket {
            TODO("Not yet implemented")
        }

    }

}