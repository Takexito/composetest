package ru.mobileup.features.collection.domain

import ru.mobileup.features.R

data class Statistic(
    val value: Int,
    val type: StatisticType,
)

enum class StatisticType {
    DOLLARS, COIN, NUMBER;

    fun getIconId(): Int {
        return when (this) {
            DOLLARS -> R.drawable.ic_baseline_attach_money_24
            COIN -> R.drawable.ic_baseline_currency_bitcoin_24
            NUMBER -> R.drawable.ic_outline_diamond_24
        }
    }
}


