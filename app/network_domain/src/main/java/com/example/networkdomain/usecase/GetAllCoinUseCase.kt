package com.example.networkdomain.usecase

import com.example.networkdomain.model.CoinsResponse
import com.example.networkdomain.network.NetworkResult
import com.example.networkdomain.network.safeApiCall
import com.example.networkdomain.repo.impl.CoinRepositoryImpl
import com.example.networkdomain.repo.interaface.CoinRepository
import com.example.networkdomain.usecase.base.BaseSuspendUseCase
import javax.inject.Inject

class GetAllCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) : BaseSuspendUseCase<NetworkResult<List<CoinsResponse>>, String>() {
    override suspend fun perform(params: String): NetworkResult<List<CoinsResponse>> {
        return when (val result = safeApiCall { repository.getAllCoins() }) {
            is NetworkResult.Success -> NetworkResult.Success(result.body, result.responseCode)
            is NetworkResult.Failure -> result
        }
    }
}