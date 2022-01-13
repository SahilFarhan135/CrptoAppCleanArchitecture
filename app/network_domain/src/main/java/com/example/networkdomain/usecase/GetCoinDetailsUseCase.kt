package com.example.networkdomain.usecase

import com.example.networkdomain.model.CoinDetailsResponse
import com.example.networkdomain.network.NetworkResult
import com.example.networkdomain.network.safeApiCall
import com.example.networkdomain.repo.interaface.CoinRepository
import com.example.networkdomain.usecase.base.BaseSuspendUseCase
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val repository: CoinRepository
) : BaseSuspendUseCase<NetworkResult<CoinDetailsResponse>, String>() {
    override suspend fun perform(params: String): NetworkResult<CoinDetailsResponse> {
        return when (val result = safeApiCall { repository.getCoinDetailsById(params) }) {
            is NetworkResult.Success -> NetworkResult.Success(result.body, result.responseCode)
            is NetworkResult.Failure -> result
        }
    }
}