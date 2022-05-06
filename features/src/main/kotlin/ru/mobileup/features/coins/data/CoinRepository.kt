package ru.mobileup.features.coins.data

import me.aartikov.replica.single.Replica
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinDetails
import ru.mobileup.features.coins.domain.CoinId

interface CoinRepository {
    fun coinList(): Replica<List<Coin>>
    fun coinDetailedWithMarkets(coinId: CoinId): Replica<CoinDetails>
}