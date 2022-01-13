package com.example.quhu.di

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.example.networkdomain.api.CoinApi
import com.example.networkdomain.network.NetworkClient
import com.example.networkdomain.network.NetworkManager
import com.example.networkdomain.repo.impl.CoinRepositoryImpl
import com.example.networkdomain.repo.interaface.CoinRepository
import com.example.networkdomain.storage.PrefsUtil
import com.example.networkdomain.usecase.GetAllCoinUseCase
import com.example.networkdomain.usecase.GetCoinDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): CoinApi {
        return retrofit.create(CoinApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(
        context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            PrefsUtil.SHARED_PREFERENCE_ID, Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun providePrefUtils(sharedPreferences: SharedPreferences): PrefsUtil {
        return PrefsUtil(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetCoinUseCase(repo: CoinRepository): GetCoinDetailsUseCase {
        return GetCoinDetailsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetAllCoinUseCase(repo: CoinRepository): GetAllCoinUseCase {
        return GetAllCoinUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideNetworkManager(
        @ApplicationContext context: Context
    ): NetworkManager {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return NetworkManager(connectivityManager)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkManager: NetworkManager
    ): OkHttpClient {
        return NetworkClient.provideOkHttp(
            networkManager
        )
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = NetworkClient.provideRetrofit(okHttpClient)


}
