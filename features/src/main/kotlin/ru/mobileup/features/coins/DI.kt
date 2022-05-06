package ru.mobileup.features.coins

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.get
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.network.BaseUrlProvider
import ru.mobileup.core.network.NetworkApiFactory
import ru.mobileup.core.network.RealBaseUrlProvider
import ru.mobileup.features.coins.data.*
import ru.mobileup.features.coins.domain.CoinId
import ru.mobileup.features.coins.ui.CoinComponent
import ru.mobileup.features.coins.ui.RealCoinComponent
import ru.mobileup.features.coins.ui.details.CoinDetailsComponent
import ru.mobileup.features.coins.ui.details.RealCoinDetailsComponent
import ru.mobileup.features.coins.ui.details.RealFlowCoinDetailsComponent
import ru.mobileup.features.coins.ui.list.CoinListComponent
import ru.mobileup.features.coins.ui.list.RealCoinListComponent
import ru.mobileup.features.coins.ui.list.RealFlowCoinListComponent

private val qualifier = StringQualifier("coinQualifier")

fun coinModule(backendUrl: String) = module {
    single<BaseUrlProvider>(qualifier) { RealBaseUrlProvider(backendUrl) }
    single(qualifier) { NetworkApiFactory(get(qualifier)) }
    single<CoinApi>(qualifier) { get<NetworkApiFactory>(qualifier).createUnauthorizedApi() }
    single<FlowCoinRepository> { RealFlowCoinRepository(get(qualifier)) }
    single<CoinRepository> { RealCoinRepository(get(), get(qualifier)) }
}

fun ComponentFactory.createCoinComponent(
    componentContext: ComponentContext
): CoinComponent {
    return RealCoinComponent(componentContext, get())
}

fun ComponentFactory.createCoinListComponent(
    componentContext: ComponentContext,
    onOutput: (CoinListComponent.Output) -> Unit
): CoinListComponent {
    return RealCoinListComponent(
        componentContext,
        onOutput,
        get<CoinRepository>().coinList(),
        get()
    )
}

fun ComponentFactory.createFlowCoinListComponent(
    componentContext: ComponentContext,
    onOutput: (CoinListComponent.Output) -> Unit
): CoinListComponent {
    return RealFlowCoinListComponent(
        componentContext,
        onOutput,
        get<FlowCoinRepository>().coinList(),
        get()
    )
}

fun ComponentFactory.createCoinDetailsComponent(
    componentContext: ComponentContext,
    coinId: CoinId
): CoinDetailsComponent {
    return RealCoinDetailsComponent(
        componentContext,
        get<CoinRepository>().coinDetailedWithMarkets(coinId),
        get()
    )
}

fun ComponentFactory.createFlowCoinDetailsComponent(
    componentContext: ComponentContext,
    coinId: CoinId
): CoinDetailsComponent {
    return RealFlowCoinDetailsComponent(
        componentContext,
        get<FlowCoinRepository>().coinDetailedWithMarkets(coinId),
        get()
    )
}