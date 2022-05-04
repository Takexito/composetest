package ru.mobileup.features.coins.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.mobileup.core.widget.LceWidget
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinId

@Composable
fun CoinListUi(
    component: CoinListComponent,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LceWidget(
            state = component.cryptoState,
            onRetryClick = component::onRetryClick,
            modifier = modifier
        ) { coins, _ ->
            if (coins.isNotEmpty()) {
                CoinList(
                    coins = coins,
                    onItemClick = component::onCoinClick
                )
            }
        }
    }
}


@Composable
fun CoinList(
    coins: List<Coin>,
    onItemClick: (CoinId) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        itemsIndexed(
            items = coins,
            key = { _, coin -> coin.id }
        ) { index, coin ->
            CoinItem(
                coin = coin,
                onClick = { onItemClick(coin.id) }
            )

            if (index != coins.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
fun CoinItem(
    coin: Coin,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                text = coin.symbol,
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = coin.name,
            )

            Text(
                modifier = Modifier
                    .defaultMinSize(minWidth = 80.dp)
                    .align(Alignment.CenterVertically),
                text = coin.price.toString(),
                textAlign = TextAlign.End
            )
        }
    }
}