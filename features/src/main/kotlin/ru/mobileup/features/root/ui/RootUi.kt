package ru.mobileup.features.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.mobileup.core.message.ui.FakeMessageComponent
import ru.mobileup.core.message.ui.MessageUi
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.utils.createFakeRouterState
import ru.mobileup.features.collection.domain.CollectionCard
import ru.mobileup.features.collection.domain.Statistic
import ru.mobileup.features.collection.domain.StatisticType
import ru.mobileup.features.collection.ui.CollectionUi
import ru.mobileup.features.collection.ui.card.CardUi
import ru.mobileup.features.pokemons.ui.FakePokemonsComponent
import ru.mobileup.features.pokemons.ui.PokemonsUi

@Composable
fun RootUi(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Children(component.routerState, modifier) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Pokemons -> PokemonsUi(instance.component)
//            is RootComponent.Child.Crypto -> CoinsUi(instance.component)
            is RootComponent.Child.Crypto -> CardUi()
            is RootComponent.Child.Crypto -> CollectionUi(
                collectionList = listOf(
                    CollectionCard("Animal", ""),
                    CollectionCard("Nature", ""),
                    CollectionCard("Image", ""),
                    CollectionCard("People", "")
                ),
                statisticList = listOf(
                    Statistic(1000, StatisticType.DOLLARS),
                    Statistic(200, StatisticType.COIN),
                    Statistic(10, StatisticType.NUMBER)
                )
            )
        }
    }

    MessageUi(
        component = component.messageComponent,
        modifier = modifier,
        bottomPadding = 16.dp
    )
}

@Preview(showSystemUi = true)
@Composable
fun RootUiPreview() {
    AppTheme {
        RootUi(FakeRootComponent())
    }
}

class FakeRootComponent : RootComponent {

    override val routerState =
        createFakeRouterState(RootComponent.Child.Pokemons(FakePokemonsComponent()))

    override val messageComponent = FakeMessageComponent()
}