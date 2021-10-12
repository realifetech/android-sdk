package com.realifetech.sdk.core.data.model.shared.`object`

import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.bearerFormat
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.clientId
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.fragmentTimeslot
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.fragmentTimeslotWithNullTranslations
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.languageText
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.mutationResponse
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.randomText
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.timeslot
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.timeslotWithNullTranslations
import com.realifetech.type.Language
import org.junit.Assert.assertEquals
import org.junit.Test

class SharedExtensionsKtTest {

    @Test
    fun getAsModel() {
        val standardResponse = mutationResponse.asModel
        assertEquals(mutationResponse.message, standardResponse.message)
        assertEquals(null, standardResponse.code)
        assertEquals(null, standardResponse.type)
    }

    @Test
    fun getToLanguage() {

        var result = languageText.toLanguage
        assertEquals(Language.EN, result)
        result = randomText.toLanguage
        assertEquals(null, result)
    }

    @Test
    fun getToBearerFormat() {
        val result = SharedMocks.token.toBearerFormat
        assertEquals(bearerFormat, result)
    }

    @Test
    fun getToClientId() {
        val result = SharedMocks.appCode.toClientId
        assertEquals(clientId, result)
    }

    @Test
    fun `fragment Time Slots as Model`() {
        var result = fragmentTimeslot.asModel
        assertEquals(timeslot, result)
        result = fragmentTimeslotWithNullTranslations.asModel
        assertEquals(timeslotWithNullTranslations, result)
    }
}