package com.realifetech.sdk.identity

import android.webkit.WebView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apollographql.apollo.exception.ApolloException
import com.realifetech.fragment.AuthToken
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.identity.data.model.RLTAliasType
import com.realifetech.sdk.identity.data.model.RLTTraitType
import com.realifetech.sdk.identity.domain.IdentityRepository
import com.realifetech.sdk.identity.mocks.IdentityMocks
import com.realifetech.sdk.identity.mocks.IdentityMocks.SALT
import com.realifetech.sdk.identity.mocks.IdentityMocks.authToken
import com.realifetech.sdk.identity.mocks.IdentityMocks.userInfo
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
    lateinit var analytics: Analytics

    @RelaxedMockK
    lateinit var configurationStorage: ConfigurationStorage

    @RelaxedMockK
    lateinit var webViewWrapper: WebViewWrapper
    lateinit var identity: Identity
    lateinit var webView: WebView
    lateinit var authenticateSlot: CapturingSlot<(token: AuthToken?, error: Exception?) -> Unit>
    lateinit var deleteMyAccountSlot: CapturingSlot<(error: Exception?, success: Boolean?) -> Unit>
    lateinit var getSsoSlot: CapturingSlot<(error: Exception?, url:String?) -> Unit>
    private lateinit var completion: (error: Exception?, result: Boolean) -> Unit
    private val testDispatcher = TestCoroutineDispatcher()
    private val aliasType = RLTAliasType.TdcAccountId
    private val providedAliasId = "aliasId"
    private val userId = "some user id"
    private val USER = "user"
    private val ALIAS = "alias"
    private val IDENTIFY = "identify"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        identity =
            Identity(
                webViewWrapper,
                identityRepository,
                testDispatcher,
                testDispatcher,
                storage,
                configurationStorage,
                analytics
            )
        authenticateSlot = slot()
        deleteMyAccountSlot= slot()
        getSsoSlot= slot()
        webView = mockk()
        configurationStorage = mockk()
        completion = mockk()
    }

    @Test
    fun `clear does set userId to null in configurationStorage`() {
        // GIVEN
        every {
            configurationStorage.userId
        } answers { null }
        // WHEN
        identity.clear()
        // THEN
        assertEquals(configurationStorage.userId, null)
    }

    @Test
    fun `identify does set the userId and sent analytics`() {
        // GIVEN
        every {
            configurationStorage.userId
        } answers { userId }

        val traits = mutableMapOf<RLTTraitType, Any>()
        traits[RLTTraitType.LastName] = "lastName"
        traits[RLTTraitType.Dynamic("dynamic value")] = 123

        val map = mutableMapOf<String, Any>()
        traits.forEach { trait ->
            map[trait.key.convertTraitToString()] = trait.value
        }

        // WHEN
        identity.identify(userId, traits, completion)
        // THEN
        assertEquals(configurationStorage.userId, userId)
        verify {
            analytics.track(
                USER,
                IDENTIFY,
                map,
                null,
                completion
            )
        }
    }

    @Test
    fun `alias sent analytics correctly`() {
        // GIVEN
        val convertedAlias = aliasType.convertAliasToString()
        val map = mapOf(convertedAlias to providedAliasId)

        // WHEN
        identity.alias(aliasType, providedAliasId, completion)
        // THEN
        verify {
            analytics.track(
                USER,
                ALIAS,
                map,
                null,
                completion
            )
        }
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
    fun `attempt to delete My Account return success`() {
        every { identity.deleteMyAccount(capture(deleteMyAccountSlot)) } answers {
            deleteMyAccountSlot.captured.invoke(null, true)
        }
        identity.deleteMyAccount { error, success ->
            Assert.assertEquals(true, success)
            Assert.assertEquals(null, error)
        }
    }

    @Test
    fun `attempt to get sso return url`() {
        every { identityRepository.getSSO(IdentityMocks.provider,capture(getSsoSlot)) } answers {
            getSsoSlot.captured.invoke(null, IdentityMocks.authUrl)
        }
        identity.getSSO(IdentityMocks.provider) { error, url ->
            Assert.assertEquals(IdentityMocks.authUrl, url)
            Assert.assertEquals(null, error)
        }
    }
    @Test
    fun `attempt to get sso return error`() {
        every { identityRepository.getSSO(IdentityMocks.provider,capture(getSsoSlot)) } answers {
            getSsoSlot.captured.invoke(ApolloException(""), null)
        }
        identity.getSSO(IdentityMocks.provider) { error, url ->
            Assert.assertEquals(null, url)
            assert(error is ApolloException)
        }
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