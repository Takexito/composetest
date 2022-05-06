package ru.mobileup.features.coins.ui.list

import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinId
import ru.mobileup.features.coins.ui.utils.toComposeState

class RealFlowCoinListComponent(
    componentContext: ComponentContext,
    private val onOutput: (CoinListComponent.Output) -> Unit,
    coinList: Flow<List<Coin>>,
    errorHandler: ErrorHandler
) : ComponentContext by componentContext, CoinListComponent {
    override val cryptoState by coinList.toComposeState(componentCoroutineScope(), errorHandler)

    override fun onCoinClick(coinId: CoinId) {
        onOutput(CoinListComponent.Output.CoinDetailsRequested(coinId))
    }

    override fun onRetryClick() {
    }
}
