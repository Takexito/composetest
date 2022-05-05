package ru.mobileup.features.coins.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import ru.mobileup.features.coins.data.dto.toDomain
import ru.mobileup.features.coins.domain.*

class RealCoinRepository(
    private val api: CoinApi,
) : CoinRepository {
    override fun coinList(): Flow<List<Coin>> {
        return flow {
            val result = api.getCoins()
            emit(result.data.map { it.toDomain() })
        }
    }

    override fun coinDetailedWithMarkets(coinId: CoinId): Flow<CoinDetails> {
        val detailedResult = coinDetailed(coinId)
        val coinMarket = coinMarkets(coinId)
        val result =
            detailedResult.combine(coinMarket) { details: CoinDetails, markets: List<CoinMarket> ->
                details.newCoinDetails(markets = markets)
            }
        return result
    }

    private fun coinDetailed(coinId: CoinId): Flow<CoinDetails> {
        return flow {
            val detailedResult = api.getCoinById(id = coinId.value)
            detailedResult.firstOrNull()?.let { detailedCoinResponse ->
                emit(detailedCoinResponse.toDomain())
            }
        }
    }

    private fun coinMarkets(coinId: CoinId): Flow<List<CoinMarket>> {
        return flow {
            emit(api.getMarketsByCoinId(id = coinId.value).map { it.toDomain() })
        }
    }
}