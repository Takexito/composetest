package ru.mobileup.features.coins.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WrapperCoinResponse<T>(
    @SerialName("data") val data: List<T>,
)