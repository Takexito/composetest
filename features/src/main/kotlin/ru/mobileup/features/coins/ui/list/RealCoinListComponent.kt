package ru.mobileup.features.coins.ui.list

import com.arkivanov.decompose.ComponentContext
import me.aartikov.replica.single.Loadable
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinId

class RealCoinListComponent(
    componentContext: ComponentContext,
    private val onOutput: (CoinListComponent.Output) -> Unit,
    private val coinList: List<Coin>,
    errorHandler: ErrorHandler
) : ComponentContext by componentContext, CoinListComponent {
    override val cryptoState: Loadable<List<Coin>>
        get() = Loadable(false, coinList)

    override fun onCoinClick(coinId: CoinId) {
        onOutput(CoinListComponent.Output.CoinDetailsRequested(coinId))
    }

    override fun onRetryClick() {
    }
}