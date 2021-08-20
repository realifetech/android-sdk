package com.realifetech.sdk.sell.weboredering

import android.view.MenuItem
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.sdk.sell.utils.CoroutinesTestRule
import com.realifetech.sdk.sell.weboredering.WebOrderingViewModel.ScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@FlowPreview
@ExperimentalCoroutinesApi
class WebOrderingViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTestRule()

    private lateinit var viewModel: WebOrderingViewModel

    @Mock
    private lateinit var view: View

    @Mock
    private lateinit var menuItem: MenuItem

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = WebOrderingViewModel()

    }


    @Test
    fun handleNavBack() = testCoroutineRule.runBlockingTest {
        val navBackListener = Channel<View>()
        viewModel.handleNavBack(navBackListener.consumeAsFlow())
        navBackListener.send(view)
        navBackListener.close()
        Assert.assertEquals(viewModel.screenState.value, ScreenState.WebViewGoBack)
    }

    @Test
    fun handleNavForward() = testCoroutineRule.runBlockingTest {
        val navForwardListener = Channel<View>()
        viewModel.handleNavForward(navForwardListener.consumeAsFlow())
        navForwardListener.send(view)
        navForwardListener.close()
        Assert.assertEquals(viewModel.screenState.value, ScreenState.WebViewGoForward)

    }

    @Test
    fun handleRefresh() = testCoroutineRule.runBlockingTest {
        val navForwardListener = Channel<MenuItem>()
        viewModel.handleRefresh(navForwardListener.consumeAsFlow())
        navForwardListener.send(menuItem)
        navForwardListener.close()
        Assert.assertEquals(viewModel.screenState.value, ScreenState.WebViewRefresh)
    }
}


