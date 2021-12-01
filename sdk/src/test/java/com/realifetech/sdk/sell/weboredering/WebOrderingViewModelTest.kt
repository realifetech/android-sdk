package com.realifetech.sdk.sell.weboredering

import android.view.MenuItem
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.sdk.sell.utils.MainCoroutineRule
import com.realifetech.sdk.sell.weboredering.WebOrderingViewModel.ScreenState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class WebOrderingViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: WebOrderingViewModel

    @MockK
    private lateinit var view: View

    @MockK
    private lateinit var menuItem: MenuItem

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = WebOrderingViewModel()
    }


    @Test
    fun handleNavBack() = runBlockingTest {
        val navBackListener = Channel<View>()
        viewModel.handleNavBack(navBackListener.consumeAsFlow())
        navBackListener.send(view)
        navBackListener.close()
        Assert.assertEquals(viewModel.screenState.value, ScreenState.WebViewGoBack)
    }

    @Test
    fun handleNavForward() = runBlockingTest {
        val navForwardListener = Channel<View>()
        viewModel.handleNavForward(navForwardListener.consumeAsFlow())
        navForwardListener.send(view)
        navForwardListener.close()
        Assert.assertEquals(viewModel.screenState.value, ScreenState.WebViewGoForward)

    }

    @Test
    fun handleRefresh() = runBlockingTest {
        val navForwardListener = Channel<MenuItem>()
        viewModel.handleRefresh(navForwardListener.consumeAsFlow())
        navForwardListener.send(menuItem)
        navForwardListener.close()
        Assert.assertEquals(viewModel.screenState.value, ScreenState.WebViewRefresh)
    }
}


