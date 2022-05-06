package ru.mobileup.features.coins.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import ru.mobileup.features.coins.domain.*

class FakeFlowCoinRepository : FlowCoinRepository {
    override fun coinList(): Flow<List<Coin>> {
        val coin123 = Coin(
            CoinId("123"),
            "MYSUPERCOIN ssssssssssss ssss ыыыы",
            "MYS123",
            123.34
        )
        val coin1 = Coin(CoinId("1"), "MYSUPERCOIN 1", "MYS1", 1.34)
        val coin12 = Coin(CoinId("12"), "MYSUPERCOIN 12", "MYS12", 12.34)

        return flow {
            emit(listOf(coin123, coin12, coin1))
        }
    }

    override fun coinDetailedWithMarkets(coinId: CoinId): Flow<CoinDetails> {
        return coinDetailed(coinId)
            .combine(coinMarkets()) { details: CoinDetails, markets: List<CoinMarket> ->
                details.newCoinDetails(markets = markets)
            }
    }

    private fun coinDetailed(coinId: CoinId): Flow<CoinDetails> {
        return flow {
            val detailed = CoinDetails(
                CoinId("123"),
                "MYSUPERCOIN ssssssssssss ssss ыыыы",
                1,
                123.33,
                listOf(),
            )
            emit(detailed)
        }
    }


    private fun coinMarkets(): Flow<List<CoinMarket>> {
        val coinMarket = CoinMarket(MarketId("marketId"), "market", 111.11)

        val list = arrayListOf(coinMarket)
        repeat(20) {
            list.add(
                CoinMarket(
                    MarketId(coinMarket.id.value + it),
                    coinMarket.name + it,
                    coinMarket.price * it
                )
            )
        }
        return flow { emit(list) }
    }

}