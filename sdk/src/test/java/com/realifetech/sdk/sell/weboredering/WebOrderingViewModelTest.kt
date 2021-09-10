package com.realifetech.sdk.sell.weboredering

import android.view.MenuItem
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.sdk.sell.utils.MainCoroutineRule
import com.realifetech.sdk.sell.weboredering.WebOrderingViewModel.ScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WebOrderingViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: WebOrderingViewModel

    @Mock
    private lateinit var view: View

    @Mock
    private lateinit var menuItem: MenuItem

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = WebOrderingViewModel()
        view = mock(View::class.java)

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


