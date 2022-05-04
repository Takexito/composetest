package ru.mobileup.features.coins.ui.details

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
import ru.mobileup.features.coins.domain.CoinDetails
import ru.mobileup.features.coins.domain.CoinMarket

@Composable
fun CoinDetailsUi(
    component: CoinDetailsComponent,
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
        ) { coin, _ ->
            Column {
                CoinDetailed(coin = coin)

                if (coin.markets.isNotEmpty()) {
                    CoinMarketList(
                        markets = coin.markets,
                    )
                }

            }

        }
    }
}

@Composable
fun CoinDetailed(
    coin: CoinDetails,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = coin.rank.toString(),
                modifier = Modifier
                    .width(60.dp)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = coin.name,
            )

            Text(
                modifier = Modifier
                    .width(80.dp),
                text = coin.priceUsd.toString(),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun CoinMarketList(
    markets: List<CoinMarket>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        itemsIndexed(
            items = markets,
            key = { _, market -> market.name }
        ) { index, market ->

            CoinMarket(market = market)

            if (index != markets.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
fun CoinMarket(
    market: CoinMarket,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = market.name,
            )

            Text(
                modifier = Modifier
                    .width(80.dp)
                    .align(Alignment.CenterVertically),
                text = market.price.toString(),
                textAlign = TextAlign.End
            )
        }
    }
}