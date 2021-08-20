package com.realifetech.sdk.sell.weboredering

import android.view.MenuItem
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WebOrderingViewModel : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    fun handleNavBack(navBackSubscriber: Flow<View>) {
        viewModelScope.launch {
            navBackSubscriber.collect {
                _screenState.value = ScreenState.WebViewGoBack
            }

        }
    }

    fun handleNavForward(navForwardSubscriber: Flow<View>) {
        viewModelScope.launch {
            navForwardSubscriber.collect {
                _screenState.value = ScreenState.WebViewGoForward
            }
        }
    }

    fun handleRefresh(refreshSubscriber: Flow<MenuItem>) {
        viewModelScope.launch {
            refreshSubscriber.collect {
                _screenState.value = ScreenState.WebViewRefresh
            }
        }
    }


    sealed class ScreenState {
        object WebViewGoForward : ScreenState()
        object WebViewGoBack : ScreenState()
        object WebViewRefresh : ScreenState()

    }
}