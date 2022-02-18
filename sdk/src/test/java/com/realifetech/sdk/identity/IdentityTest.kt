package com.realifetech.sdk.identity

import android.webkit.WebView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.fragment.AuthToken
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.identity.domain.IdentityRepository
import com.realifetech.sdk.identity.mocks.IdentityMocks.SALT
import com.realifetech.sdk.identity.mocks.IdentityMocks.authToken
import com.realifetech.sdk.identity.mocks.IdentityMocks.userInfo
import com.realifetech.sdk.identity.sso.SSOFeature
import com.realifetech.sdk.sell.utils.MainCoroutineRule
import com.realifetech.sdk.sell.weboredering.WebViewWrapper
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class IdentityTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var identityRepository: IdentityRepository

    @RelaxedMockK
    lateinit var storage: AuthTokenStorage

    @RelaxedMockK
    lateinit var ssoFeature: SSOFeature

    @RelaxedMockK
    lateinit var analytics: Analytics

    @RelaxedMockK
    lateinit var configurationStorage: ConfigurationStorage

    @RelaxedMockK
    lateinit var webViewWrapper: WebViewWrapper
    lateinit var identity: Identity
    lateinit var webView: WebView
    lateinit var authenticateSlot: CapturingSlot<(token: AuthToken?, error: Exception?) -> Unit>
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        identity =
            Identity(
                webViewWrapper,
                identityRepository,
                testDispatcher,
                testDispatcher,
                ssoFeature,
                storage,
                configurationStorage,
                analytics
            )
        authenticateSlot = slot()
        webView = mockk()
    }

    @Test
    fun logout() {
        identity.logout()
        verify {
            webViewWrapper.clearCacheAndStorage()
        }
    }

    @Test
    fun `attempt Login while webView reference is null results successfully `() = runBlockingTest {
        `Repository Attempt Login Successfully`()
        every { webViewWrapper.webView } returns null

        identity.attemptLogin(
            userInfo.email,
            userInfo.firstName.value,
            userInfo.lastName.value,
            SALT
        ) {
            assertEquals(null, it)
        }

        verify { storage.webAuthToken = authToken }
    }

    @Test
    fun `attempt Login while webView reference stored results successfully `() = runBlockingTest {
        `Repository Attempt Login Successfully`()
        every { webViewWrapper.webView } returns webView

        identity.attemptLogin(
            userInfo.email,
            userInfo.firstName.value,
            userInfo.lastName.value,
            SALT
        ) {
            assertEquals(null, it)
        }

        verify { webViewWrapper.authenticate(authToken) }
    }

    @Test
    fun `attempt Login  complete with exception `() = runBlockingTest {
        every {
            identityRepository.attemptToLogin(
                userInfo.email,
                userInfo.firstName.value,
                userInfo.lastName.value,
                SALT,
                capture(authenticateSlot)
            )
        } answers { authenticateSlot.captured.invoke(null, Exception()) }

        identity.attemptLogin(
            userInfo.email,
            userInfo.firstName.value,
            userInfo.lastName.value,
            SALT
        ) {
            assert(it is Exception)
        }
    }

    @Test
    fun getSSO() {
        val sso = identity.getSSO()
        Assert.assertEquals(ssoFeature, sso)
    }

    private fun `Repository Attempt Login Successfully`() {
        every {
            identityRepository.attemptToLogin(
                userInfo.email,
                userInfo.firstName.value,
                userInfo.lastName.value,
                SALT,
                capture(authenticateSlot)
            )
        } answers { authenticateSlot.captured.invoke(authToken, null) }
    }

}