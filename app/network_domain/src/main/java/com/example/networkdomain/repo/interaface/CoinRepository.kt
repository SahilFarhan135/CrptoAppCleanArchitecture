package com.example.networkdomain.repo.interaface

import com.example.networkdomain.model.CoinDetailsResponse
import com.example.networkdomain.model.CoinsResponse
import retrofit2.Response

interface CoinRepository {
    suspend fun getAllCoins(): Response<List<CoinsResponse>>
    suspend fun getCoinDetailsById(coinId: String): Response<CoinDetailsResponse>
}