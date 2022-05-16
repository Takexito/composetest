package ru.mobileup.features.collection.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mobileup.core.theme.backgroundColor
import ru.mobileup.features.R

//@Preview(name = "NEXUS_7", device = Devices.NEXUS_7)
//@Preview(name = "NEXUS_7_2013", device = Devices.NEXUS_7_2013)
//@Preview(name = "NEXUS_5", device = Devices.NEXUS_5)
//@Preview(name = "NEXUS_6", device = Devices.NEXUS_6)
//@Preview(name = "NEXUS_9", device = Devices.NEXUS_9)
//@Preview(name = "NEXUS_10", device = Devices.NEXUS_10)
//@Preview(name = "NEXUS_5X", device = Devices.NEXUS_5X)
//@Preview(name = "NEXUS_6P", device = Devices.NEXUS_6P)
//@Preview(name = "PIXEL_C", device = Devices.PIXEL_C)
//@Preview(name = "PIXEL", device = Devices.PIXEL)
//@Preview(name = "PIXEL_XL", device = Devices.PIXEL_XL)
//@Preview(name = "PIXEL_2", device = Devices.PIXEL_2)
//@Preview(name = "PIXEL_2_XL", device = Devices.PIXEL_2_XL)
//@Preview(name = "PIXEL_3", device = Devices.PIXEL_3)
//@Preview(name = "PIXEL_3_XL", device = Devices.PIXEL_3_XL)
//@Preview(name = "PIXEL_3A", device = Devices.PIXEL_3A)
//@Preview(name = "PIXEL_3A_XL", device = Devices.PIXEL_3A_XL)
//@Preview(name = "PIXEL_4", device = Devices.PIXEL_4)
//@Preview(name = "PIXEL_4_XL", device = Devices.PIXEL_4_XL)
//@Preview(name = "AUTOMOTIVE_1024p", device = Devices.AUTOMOTIVE_1024p)
@Preview
@Composable
fun CollectionUiFake() {
    CollectionUi(listOf(Any(), Any(), Any()), listOf(Any(), Any(), Any()))
}

@Composable
fun CollectionUi(collectionList: List<CollectionItemType>, statisticList: List<HeaderStatistic>) {

    val topBarButtons = listOf(
        TopBarButtonConfig(
            id = "Cart",
            iconPainter = painterResource(id = R.drawable.ic_cart_24)
        ),
        TopBarButtonConfig(
            id = "Cart",
            iconPainter = painterResource(id = R.drawable.ic_menu_24)
        )
    )
    val bottomBarItems = listOf(
        BottomBarItemConfig(
            painter = painterResource(id = R.drawable.ic_outline_pie_chart_24)
        ),
        BottomBarItemConfig(
            painter = painterResource(id = R.drawable.ic_favorite_border_24)
        ),
        BottomBarItemConfig(
            painter = painterResource(id = R.drawable.ic_baseline_qr_code_2_24)
        ),
        BottomBarItemConfig(
            painter = painterResource(id = R.drawable.ic_outline_leaderboard_24)
        ),
    )

    Scaffold(
        backgroundColor = backgroundColor,
        topBar = {
            CollectionTopAppBar(
                buttons = topBarButtons
            ) { buttonId ->

            }
        },
        bottomBar = {
            BottomBar(
                items = bottomBarItems
            ) { pos ->

            }
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .verticalScroll(rememberScrollState()),
        ) {
            CollectionHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                statistics = statisticList
            ) { pos ->

            }
            CollectionCardList(collections = collectionList) { item, index ->

            }
        }
    }
}


