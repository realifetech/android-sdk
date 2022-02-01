package com.realifetech.sdk.core.utils

import com.realifetech.sdk.identity.mocks.IdentityMocks
import com.realifetech.sdk.identity.mocks.IdentityMocks.HASHED_VALUE
import com.realifetech.sdk.identity.mocks.IdentityMocks.SALT
import org.junit.Assert

import org.junit.Test

class StringExtensionsKtTest {

    @Test
    fun ` hash encoded value using sha256`() {
        val encodedValue = "${IdentityMocks.DICTIONARY}.${SALT}"
        val hash = encodedValue.sha256()
        Assert.assertEquals(HASHED_VALUE, hash)

    }


}