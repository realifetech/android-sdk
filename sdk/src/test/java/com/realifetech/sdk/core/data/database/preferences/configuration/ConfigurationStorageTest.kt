package com.realifetech.sdk.core.data.database.preferences.configuration

import android.content.Context
import androidx.core.content.edit
import com.realifetech.sdk.core.data.constants.ConfigConstants
import com.realifetech.sdk.core.data.model.shared.translation.EMPTY
import com.realifetech.sdk.core.mocks.ConfigurationMocks
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ConfigurationStorageTest {
    @RelaxedMockK
    lateinit var context: Context
    private lateinit var configurationStorage: ConfigurationStorage

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        configurationStorage = ConfigurationStorage(context)
    }

    @Test
    fun `get Api Url`() {
        every {
            configurationStorage.preferences.getString(API_URL, ConfigConstants.apiUrl)
        } returns ConfigurationMocks.API_URL
        val result = configurationStorage.apiUrl
        Assert.assertEquals(ConfigurationMocks.API_URL, result)
    }

    @Test
    fun `get Default Api Url`() {
        every {
            configurationStorage.preferences.getString(API_URL, ConfigConstants.apiUrl)
        } returns ConfigConstants.apiUrl
        val result = configurationStorage.apiUrl
        Assert.assertEquals("${ConfigConstants.apiUrl}/", result)
    }
    @Test
    fun `get EMPTY Api Url`() {
        every {
            configurationStorage.preferences.getString(API_URL, ConfigConstants.apiUrl)
        } returns null
        val result = configurationStorage.apiUrl
        Assert.assertEquals(BACK_SLASH, result)
    }

    @Test
    fun `set Api Url`() {
        configurationStorage.apiUrl = ConfigConstants.apiUrl
        verify {
            configurationStorage.preferences.edit {
                putString(
                    API_URL,
                    ConfigConstants.apiUrl
                )
            }
        }
    }

    @Test
    fun `get GraphQL Url`() {
        every {
            configurationStorage.preferences.getString(GRAPHQL_URL, ConfigConstants.graphApiUrl)
        } returns ConfigurationMocks.GRAPHQL_URL
        val result = configurationStorage.graphApiUrl
        Assert.assertEquals(ConfigurationMocks.GRAPHQL_URL, result)
    }

    @Test
    fun `get EMPTY GraphQL Url`() {
        every {
            configurationStorage.preferences.getString(GRAPHQL_URL, ConfigConstants.graphApiUrl)
        } returns null
        val result = configurationStorage.graphApiUrl
        Assert.assertEquals(EMPTY, result)
    }
    @Test
    fun `get Default GraphQL Url`() {
        every {
            configurationStorage.preferences.getString(GRAPHQL_URL, ConfigConstants.graphApiUrl)
        } returns ConfigConstants.graphApiUrl
        val result = configurationStorage.graphApiUrl
        Assert.assertEquals(ConfigConstants.graphApiUrl, result)
    }

    @Test
    fun `set GraphQL Url`() {
        configurationStorage.graphApiUrl = ConfigurationMocks.GRAPHQL_URL
        verify {
            configurationStorage.preferences.edit {
                putString(
                    GRAPHQL_URL,
                    ConfigurationMocks.GRAPHQL_URL
                )
            }
        }

    }

    @Test
    fun `get Web Ordering Journey Url`() {
        every {
            configurationStorage.preferences.getString(
                ORDERING_JOURNEY_URL,
                ConfigConstants.webOrderingJourneyUrl
            )
        } returns ConfigurationMocks.WEB_ORDERING_URL
        val result = configurationStorage.webOrderingJourneyUrl
        Assert.assertEquals(ConfigurationMocks.WEB_ORDERING_URL, result)

    }

    @Test
    fun `get EMPTY Web Ordering Journey Url`() {
        every {
            configurationStorage.preferences.getString(
                ORDERING_JOURNEY_URL,
                ConfigConstants.webOrderingJourneyUrl
            )
        } returns null
        val result = configurationStorage.webOrderingJourneyUrl
        Assert.assertEquals(EMPTY, result)
    }
    @Test
    fun `get Default Web Ordering Journey Url`() {
        every {
            configurationStorage.preferences.getString(
                ORDERING_JOURNEY_URL,
                ConfigConstants.webOrderingJourneyUrl
            )
        } returns ConfigConstants.webOrderingJourneyUrl
        val result = configurationStorage.webOrderingJourneyUrl
        Assert.assertEquals(ConfigConstants.webOrderingJourneyUrl, result)
    }


    @Test
    fun `set Web Ordering Journey Url`() {
        configurationStorage.webOrderingJourneyUrl = ConfigurationMocks.WEB_ORDERING_URL
        verify {
            configurationStorage.preferences.edit {
                putString(
                    ORDERING_JOURNEY_URL,
                    ConfigurationMocks.WEB_ORDERING_URL
                )
            }
        }
    }

    @Test
    fun `get Client Secret`() {
        every {
            configurationStorage.preferences.getString(
                CLIENT_SECRET,
                ConfigConstants.clientSecret
            )
        } returns ConfigurationMocks.CLIENT_SECRET
        val result = configurationStorage.clientSecret
        Assert.assertEquals(ConfigurationMocks.CLIENT_SECRET, result)
    }

    @Test
    fun `get default Client Secret`() {
        every {
            configurationStorage.preferences.getString(
                CLIENT_SECRET,
                ConfigConstants.clientSecret
            )
        } returns ConfigConstants.clientSecret
        val result = configurationStorage.clientSecret
        Assert.assertEquals(ConfigConstants.clientSecret, result)
    }


    @Test
    fun setClientSecret() {
        configurationStorage.clientSecret = ConfigurationMocks.CLIENT_SECRET
        verify {
            configurationStorage.preferences.edit {
                putString(
                    CLIENT_SECRET,
                    ConfigurationMocks.CLIENT_SECRET
                )
            }
        }
    }

    @Test
    fun `get APP CODE`() {
        every {
            configurationStorage.preferences.getString(
                APP_CODE,
                ConfigConstants.appCode
            )
        } returns ConfigurationMocks.APP_CODE
        val result = configurationStorage.appCode
        Assert.assertEquals(ConfigurationMocks.APP_CODE, result)
    }

    @Test
    fun `get default APP CODE`() {
        every {
            configurationStorage.preferences.getString(
                APP_CODE,
                EMPTY
            )
        } returns null
        val result = configurationStorage.appCode
        Assert.assertEquals(EMPTY, result)
    }


    @Test
    fun setAppCode() {
        configurationStorage.appCode = ConfigurationMocks.APP_CODE
        verify {
            configurationStorage.preferences.edit {
                putString(
                    APP_CODE,
                    ConfigurationMocks.APP_CODE
                )
            }
        }
    }

    @Test
    fun `test set configuration`() {
        configurationStorage.set(ConfigurationMocks.configuration)
        verifySetConfiguration()
        ConfigurationMocks.changeableConfiguration.apply {
            apiUrl = ConfigurationMocks.API_URL
            graphApiUrl = ConfigurationMocks.GRAPHQL_URL
            webOrderingJourneyUrl = ConfigurationMocks.WEB_ORDERING_URL
        }
        configurationStorage.set(ConfigurationMocks.changeableConfiguration)
    }

    private fun verifySetConfiguration() {
        verify {
            configurationStorage.preferences.edit {
                putString(
                    APP_CODE,
                    ConfigurationMocks.APP_CODE
                )
            }
            configurationStorage.preferences.edit {
                putString(
                    CLIENT_SECRET,
                    ConfigurationMocks.CLIENT_SECRET
                )
            }
            configurationStorage.preferences.edit {
                putString(
                    ORDERING_JOURNEY_URL,
                    ConfigurationMocks.WEB_ORDERING_URL
                )
            }
            configurationStorage.preferences.edit {
                putString(
                    GRAPHQL_URL,
                    ConfigurationMocks.GRAPHQL_URL
                )
            }
            configurationStorage.preferences.edit {
                putString(
                    API_URL,
                    ConfigurationMocks.API_URL
                )
            }
        }
    }

    @Test
    fun `test set default configuration`() {
        configurationStorage.set(ConfigurationMocks.defaultConfiguration)
        verify {
            configurationStorage.preferences.edit {
                putString(
                    APP_CODE,
                    ConfigurationMocks.APP_CODE
                )
            }
            configurationStorage.preferences.edit {
                putString(
                    CLIENT_SECRET,
                    ConfigurationMocks.CLIENT_SECRET
                )
            }
            configurationStorage.preferences.edit {
                putString(
                    ORDERING_JOURNEY_URL,
                    ConfigConstants.webOrderingJourneyUrl
                )
            }
            configurationStorage.preferences.edit {
                putString(
                    GRAPHQL_URL,
                    ConfigConstants.graphApiUrl
                )
            }
            configurationStorage.preferences.edit {
                putString(
                    API_URL,
                    ConfigConstants.apiUrl
                )
            }
        }
    }

    @Test
    fun `get Device Id`() {
        every {
            configurationStorage.preferences.getString(
                DEVICE_ID,
                EMPTY
            )
        } returns ConfigurationMocks.DEVICE_ID
        val result = configurationStorage.deviceId
        Assert.assertEquals(ConfigurationMocks.DEVICE_ID, result)
    }

    @Test
    fun `get Default Device Id`() {
        every {
            configurationStorage.preferences.getString(
                DEVICE_ID,
                EMPTY
            )
        } returns null
        val result = configurationStorage.deviceId
        Assert.assertEquals(EMPTY, result)
    }

    @Test
    fun `set Device Id`() {
        configurationStorage.deviceId = ConfigurationMocks.DEVICE_ID
        verify {
            configurationStorage.preferences.edit()
                .putString(DEVICE_ID, ConfigurationMocks.DEVICE_ID).apply()
        }
    }

    companion object {
        private const val APP_CODE = "app_code"
        private const val CLIENT_SECRET = "client_secret"
        private const val ORDERING_JOURNEY_URL = "ordering_journey_url"
        private const val API_URL = "api_url"
        private const val GRAPHQL_URL = "graphql_url"
        private const val DEVICE_ID = "device_id"
        private const val BACK_SLASH = "/"
    }
}