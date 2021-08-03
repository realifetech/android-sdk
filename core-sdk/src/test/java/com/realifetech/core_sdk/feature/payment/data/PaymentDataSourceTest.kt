package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyPaymentIntentQuery
import com.realifetech.GetMyPaymentIntentQuery.GetMyPaymentIntent
import com.realifetech.core_sdk.domain.Result
import com.realifetech.fragment.PaymentIntent
import com.realifetech.type.OrderType
import com.realifetech.type.PaymentStatus
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class PaymentDataSourceTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient
    private lateinit var paymentDataSource: PaymentDataSource
    private val paymentIntent = PaymentIntent(
        "PaymentIntent",
        "",
        OrderType.FOOD_AND_BEVERAGE,
        "123",
        PaymentStatus.SUCCEEDED,
        null,
        123,
        "",
        false,
        null,
        false,
        null,
        null
    )
    private val data = GetMyPaymentIntentQuery.Data(
        GetMyPaymentIntent("", GetMyPaymentIntent.Fragments(paymentIntent))
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        paymentDataSource = PaymentDataSource(apolloClient)
    }

    @Test
    fun `get My Payment Intent successfully`() = runBlocking {
        //Given
        val responseData = mockk<Response<GetMyPaymentIntentQuery.Data>>()
        every {
            responseData.data
        } returns data
        val slot = CapturingSlot<ApolloCall.Callback<GetMyPaymentIntentQuery.Data>>()
        every {
            apolloClient.query(GetMyPaymentIntentQuery(PAYMENT_INTENT_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build().enqueue(capture(slot))
        } answers {
            slot.captured.onResponse(responseData)
        }
        // When
        val result = paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID)
        // Then
        Assert.assertEquals(Result.Success(paymentIntent), result)
    }

    @Test
    fun `get My Payment Intent results with failure`() = runBlocking {
        //Given
        val responseData = mockk<Response<GetMyPaymentIntentQuery.Data>>()
        every {
            responseData.data
        } throws ApolloHttpException(any())
        val slot = CapturingSlot<ApolloCall.Callback<GetMyPaymentIntentQuery.Data>>()
        every {
            apolloClient.query(GetMyPaymentIntentQuery(PAYMENT_INTENT_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build().enqueue(capture(slot))
        } answers {
            slot.captured.onResponse(responseData)
        }
        // When
        val result = paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID)
        // Then
        assert( result is Result.Error )
        print(result)
    }

    companion object {
        private const val PAYMENT_INTENT_ID = "2"
    }
}