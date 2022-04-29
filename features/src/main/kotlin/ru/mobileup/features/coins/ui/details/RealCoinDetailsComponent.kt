package ru.mobileup.features.coins.ui.details

import com.arkivanov.decompose.ComponentContext
import me.aartikov.replica.single.Loadable
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.features.coins.domain.CoinDetails

class RealCoinDetailsComponent(
    componentContext: ComponentContext,
    private val coinDetails: CoinDetails,
    errorHandler: ErrorHandler
): ComponentContext by componentContext, CoinDetailsComponent {
    override val cryptoState: Loadable<CoinDetails>
        get() = Loadable(false, coinDetails)

    override fun onRetryClick() {

    }
}