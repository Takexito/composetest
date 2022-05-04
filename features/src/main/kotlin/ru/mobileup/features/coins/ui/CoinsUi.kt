package ru.mobileup.features.coins.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.mobileup.features.coins.ui.details.CoinDetailsUi
import ru.mobileup.features.coins.ui.list.CoinListUi

@Composable
fun CoinsUi(
    component: CoinComponent,
    modifier: Modifier = Modifier
){
    Children(component.routerState, modifier) { child ->
        when (val instance = child.instance){
            is CoinComponent.Child.Details -> CoinDetailsUi(instance.component)
            is CoinComponent.Child.List -> CoinListUi(instance.component)
        }
    }
}