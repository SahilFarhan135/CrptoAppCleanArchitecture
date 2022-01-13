package com.example.networkdomain.repo.impl

import com.example.networkdomain.api.CoinApi
import com.example.networkdomain.model.CoinDetailsResponse
import com.example.networkdomain.model.CoinsResponse
import com.example.networkdomain.repo.interaface.CoinRepository
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi: CoinApi
) : CoinRepository {


    override suspend fun getAllCoins(): Response<List<CoinsResponse>> {
        return coinApi.getCoinsList()
    }

    override suspend fun getCoinDetailsById(coinId: String): Response<CoinDetailsResponse> {
        return coinApi.getCoinDetails(coinId)
    }
}
