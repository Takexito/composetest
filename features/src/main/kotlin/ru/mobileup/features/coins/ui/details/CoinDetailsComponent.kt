package ru.mobileup.features.coins.ui.details

import androidx.compose.runtime.State
import me.aartikov.replica.single.Loadable
import ru.mobileup.features.coins.domain.CoinDetails

interface CoinDetailsComponent {

    val cryptoState: Loadable<CoinDetails>

    fun onRetryClick()
}