package com.example.quhu.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkdomain.model.CoinsResponse
import com.example.networkdomain.network.NetworkResult
import com.example.networkdomain.usecase.GetAllCoinUseCase
import com.example.quhu.base.ViewState
import com.example.quhu.util.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCoinUseCase: GetAllCoinUseCase,
) : ViewModel() {


    private val _viewState = MutableLiveData<ViewState>(ViewState.Idle)
    val viewState = _viewState.toLiveData()

    private val _allCoins = MutableLiveData<List<CoinsResponse>?>()
    val allCoins = _allCoins.toLiveData()


    fun getAllList() {
        launch {
            _viewState.postValue(ViewState.Loading)
            when (val result = getAllCoinUseCase.perform("")) {
                is NetworkResult.Success -> {
                    _allCoins.postValue(result.body)
                    _viewState.postValue(ViewState.Success())
                }
                is NetworkResult.Failure -> throw result.throwable
            }
        }
    }


    private fun launch(
        code: suspend CoroutineScope.() -> Unit
    ) {
        (viewModelScope + exceptionHandler).launch {
            code.invoke(this)
        }
    }


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleFailure(throwable = exception)
    }

    private fun handleFailure(throwable: Throwable?) {
        _viewState.postValue(ViewState.Error(throwable))
        Log.e("NETWORK_ERROR", throwable.toString())
    }


}

