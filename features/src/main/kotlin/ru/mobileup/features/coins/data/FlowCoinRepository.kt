package ru.mobileup.features.coins.data

import kotlinx.coroutines.flow.Flow
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinDetails
import ru.mobileup.features.coins.domain.CoinId
import ru.mobileup.features.coins.domain.CoinMarket


interface FlowCoinRepository {
    fun coinList(): Flow<List<Coin>>
    fun coinDetailedWithMarkets(coinId: CoinId): Flow<CoinDetails>
}