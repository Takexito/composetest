package ru.mobileup.features.coins.ui.details

import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import me.aartikov.replica.single.Loadable
import me.aartikov.replica.single.Replica
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.observe
import ru.mobileup.features.coins.domain.CoinDetails

class RealCoinDetailsComponent(
    componentContext: ComponentContext,
    private val coinDetailsReplica: Replica<CoinDetails>,
    errorHandler: ErrorHandler
) : ComponentContext by componentContext, CoinDetailsComponent {

    override val cryptoState: Loadable<CoinDetails> by coinDetailsReplica.observe(
        lifecycle,
        errorHandler
    )

    override fun onRetryClick() {
        coinDetailsReplica.refresh()
    }
}