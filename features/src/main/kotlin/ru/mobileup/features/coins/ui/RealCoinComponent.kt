package ru.mobileup.features.coins.ui

import android.os.Parcelable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import kotlinx.parcelize.Parcelize
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.coins.createCoinDetailsComponent
import ru.mobileup.features.coins.createCoinListComponent
import ru.mobileup.features.coins.domain.CoinId
import ru.mobileup.features.coins.ui.list.CoinListComponent

class RealCoinComponent(
    componentContext: ComponentContext,
    private val componentFactory: ComponentFactory
) : ComponentContext by componentContext, CoinComponent {

    private val router = router<ChildConfig, CoinComponent.Child>(
        initialConfiguration = ChildConfig.List,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: RouterState<*, CoinComponent.Child>
            by router.state.toComposeState(lifecycle)

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): CoinComponent.Child {
        return when (config) {
            is ChildConfig.List -> {
                CoinComponent.Child.List(
                    componentFactory.createCoinListComponent(componentContext, ::onCoinListOutput)
                )
            }
            is ChildConfig.Details -> {
                CoinComponent.Child.Details(
                    componentFactory.createCoinDetailsComponent(componentContext, config.id)
                )
            }
        }
    }

    private fun onCoinListOutput(output: CoinListComponent.Output) {
        when (output) {
            is CoinListComponent.Output.CoinDetailsRequested -> {
                router.push(ChildConfig.Details(output.coinId))
            }
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object List : ChildConfig

        @Parcelize
        data class Details(val id: CoinId) : ChildConfig
    }
}