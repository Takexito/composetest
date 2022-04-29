package ru.mobileup.features.coins.ui

import com.arkivanov.decompose.router.RouterState
import ru.mobileup.features.coins.ui.details.CoinDetailsComponent
import ru.mobileup.features.coins.ui.list.CoinListComponent

interface CoinComponent {
    val routerState: RouterState<*, Child>

    sealed interface Child {
        class List(val component: CoinListComponent) : Child
        class Details(val component: CoinDetailsComponent) : Child
    }
}