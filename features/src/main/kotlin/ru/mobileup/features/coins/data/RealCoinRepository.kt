package ru.mobileup.features.coins.data

import kotlinx.coroutines.delay
import me.aartikov.replica.algebra.combineEager
import me.aartikov.replica.algebra.withKey
import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.keyed.KeyedPhysicalReplica
import me.aartikov.replica.keyed.KeyedReplicaSettings
import me.aartikov.replica.single.Replica
import me.aartikov.replica.single.ReplicaSettings
import ru.mobileup.features.coins.data.dto.toDomain
import ru.mobileup.features.coins.domain.Coin
import ru.mobileup.features.coins.domain.CoinDetails
import ru.mobileup.features.coins.domain.CoinId
import ru.mobileup.features.coins.domain.CoinMarket
import kotlin.time.Duration.Companion.seconds

class RealCoinRepository(
    private val replicaClient: ReplicaClient,
    private val api: CoinApi
) : CoinRepository {
    override fun coinList(): Replica<List<Coin>> {
        return replicaClient.createReplica(
            name = "coinList",
            settings = ReplicaSettings(staleTime = 10.seconds, clearTime = 60.seconds)
        ) {
            api.getCoins().data.map { it.toDomain() }
        }
    }

    override fun coinDetailedWithMarkets(coinId: CoinId): Replica<CoinDetails> {
        return combineEager(
            coinDetailed().withKey(coinId),
            coinMarkets().withKey(coinId)
        ) { detailed, markets ->
            if (detailed == null) return@combineEager CoinDetails.emptyWithMarkets(markets)
            if (markets == null) return@combineEager detailed
            detailed.copy(markets = markets)
        }
    }

    private fun coinDetailed(): KeyedPhysicalReplica<CoinId, CoinDetails> {
        return replicaClient.createKeyedReplica(
            name = "coinDetailed",
            childName = { coinId -> "coinId = ${coinId.value}" },
            settings = KeyedReplicaSettings(maxCount = 5),
            childSettings = {
                ReplicaSettings(staleTime = 10.seconds)
            }
        ) { coinId ->
            api.getCoinById(coinId.value).first().toDomain()
        }
    }

    private fun coinMarkets(): KeyedPhysicalReplica<CoinId, List<CoinMarket>> {
        return replicaClient.createKeyedReplica(
            name = "coinMarkets",
            childName = { coinId -> "coinId = ${coinId.value}" },
            settings = KeyedReplicaSettings(maxCount = 5),
            childSettings = {
                ReplicaSettings(staleTime = 10.seconds)
            }
        ) { coinId ->
            api.getMarketsByCoinId(coinId.value).map { it.toDomain() }
        }
    }
}