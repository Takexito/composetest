package ru.mobileup.features.coins.ui.list

import me.aartikov.replica.single.Loadable
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinId

interface CoinListComponent {

    val cryptoState: Loadable<List<Coin>>

    fun onCoinClick(coinId: CoinId)
    fun onRetryClick()

    sealed interface Output {
        data class CoinDetailsRequested(val coinId: CoinId) : Output
    }
}