package ru.mobileup.features.coins.data

import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinDetails
import ru.mobileup.features.coins.domain.CoinId
import ru.mobileup.features.coins.domain.CoinMarket


interface CoinRepository {
    fun coinList(): List<Coin>
    fun coinDetailed(coinId: CoinId): CoinDetails
    fun coinMarkets(): List<CoinMarket>

    class Fake : CoinRepository {
        override fun coinList(): List<Coin> {
            val coin123 = Coin(
                CoinId("123"),
                "MYSUPERCOIN ssssssssssss ssss ыыыы",
                "MYS123",
                123.34
            )
            val coin1 = Coin(CoinId("1"), "MYSUPERCOIN 1", "MYS1", 1.34)
            val coin12 = Coin(CoinId("12"), "MYSUPERCOIN 12", "MYS12", 12.34)

            return listOf(coin123, coin12, coin1)
        }

        override fun coinDetailed(coinId: CoinId): CoinDetails {
            val detailed = CoinDetails(CoinId("123"), "MYSUPERCOIN", 1, 123.33, coinMarkets())
            return detailed
        }

        override fun coinMarkets(): List<CoinMarket> {
            val coinMarket = CoinMarket("market", 111.11)

            val list = arrayListOf(coinMarket)
            repeat(20){
                list.add(CoinMarket(coinMarket.name+it, coinMarket.price*it))
            }
            return list
        }

    }

}