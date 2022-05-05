package ru.mobileup.features.coins.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class MarketId(val value: String) : Parcelable

data class CoinMarket(
    val id: MarketId,
    val name: String,
    val price: Double
)
