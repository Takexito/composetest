package ru.mobileup.features.coins

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.get
import org.koin.dsl.module
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.coins.data.CoinRepository
import ru.mobileup.features.coins.domain.CoinId
import ru.mobileup.features.coins.ui.CoinComponent
import ru.mobileup.features.coins.ui.RealCoinComponent
import ru.mobileup.features.coins.ui.details.CoinDetailsComponent
import ru.mobileup.features.coins.ui.details.RealCoinDetailsComponent
import ru.mobileup.features.coins.ui.list.CoinListComponent
import ru.mobileup.features.coins.ui.list.RealCoinListComponent

val coinModule = module {
    single<CoinRepository> { CoinRepository.Fake() }
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

fun ComponentFactory.createCoinDetailsComponent(
    componentContext: ComponentContext,
    coinId: CoinId
): CoinDetailsComponent {
    return RealCoinDetailsComponent(
        componentContext,
        get<CoinRepository>().coinDetailed(coinId),
        get()
    )
}