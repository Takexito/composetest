package ru.mobileup.features.coins.ui.list

import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import me.aartikov.replica.single.Replica
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.observe
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinId

class RealCoinListComponent(
    componentContext: ComponentContext,
    private val onOutput: (CoinListComponent.Output) -> Unit,
    private val coinListReplica: Replica<List<Coin>>,
    errorHandler: ErrorHandler
) : ComponentContext by componentContext, CoinListComponent {
    override val cryptoState by coinListReplica.observe(lifecycle, errorHandler)

    override fun onCoinClick(coinId: CoinId) {
        onOutput(CoinListComponent.Output.CoinDetailsRequested(coinId))
    }

    override fun onRetryClick() {
        coinListReplica.refresh()
    }
}
