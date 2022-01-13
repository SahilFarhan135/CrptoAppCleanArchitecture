package com.example.networkdomain.api

import com.example.networkdomain.model.CoinDetailsResponse
import com.example.networkdomain.model.CoinsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {

    @GET(EndPoint.GET_COINS_LIST)
    suspend fun getCoinsList(): Response<List<CoinsResponse>>

    @GET(EndPoint.GET_COIN_DETAILS_BY_ID)
    suspend fun getCoinDetails(@Path("coinId") coinId: String): Response<CoinDetailsResponse>


    /*  @GET("/v1/coins")
      suspend fun getCoinsList(): List<CoinsResponse>

      @GET("/v1/coins/{coinId}")
      suspend fun getCoinDetails(@Path("coinId") coinId: String): CoinDetailsRespons*/
}