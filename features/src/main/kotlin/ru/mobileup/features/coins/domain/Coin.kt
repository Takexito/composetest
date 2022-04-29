package ru.mobileup.features.coins.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class CoinId(val value: String) : Parcelable

data class Coin(
    val id: CoinId,
    val name: String,
    val symbol: String,
    val price: Int
)
