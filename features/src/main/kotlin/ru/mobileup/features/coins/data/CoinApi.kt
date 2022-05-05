package ru.mobileup.features.coins.data

import retrofit2.http.GET
import retrofit2.http.Query
import ru.mobileup.features.coins.data.dto.CoinResponse
import ru.mobileup.features.coins.data.dto.DetailedCoinResponse
import ru.mobileup.features.coins.data.dto.MarketsCoinResponse
import ru.mobileup.features.coins.data.dto.WrapperCoinResponse

interface CoinApi {

    @GET("/api/tickers/")
    suspend fun getCoins(): WrapperCoinResponse<CoinResponse>

    @GET("/api/ticker/")
    suspend fun getCoinById(@Query("id") id: String): List<DetailedCoinResponse>

    @GET("/api/coin/markets/")
    suspend fun getMarketsByCoinId(@Query("id") id: String): List<MarketsCoinResponse>
}