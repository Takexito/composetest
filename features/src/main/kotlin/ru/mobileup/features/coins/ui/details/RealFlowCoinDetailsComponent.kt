package ru.mobileup.features.coins.ui.details

import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.features.coins.domain.CoinDetails
import ru.mobileup.features.coins.ui.utils.toComposeState

class RealFlowCoinDetailsComponent(
    componentContext: ComponentContext,
    coinDetails: Flow<CoinDetails>,
    errorHandler: ErrorHandler
) : ComponentContext by componentContext, CoinDetailsComponent {
    override val cryptoState by coinDetails.toComposeState(
        componentCoroutineScope(),
        errorHandler
    )

    override fun onRetryClick() {

    }
}