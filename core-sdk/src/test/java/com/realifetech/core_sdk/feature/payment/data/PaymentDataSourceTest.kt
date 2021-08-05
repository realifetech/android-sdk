package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyPaymentIntentQuery
import com.realifetech.fragment.PaymentIntent
import com.realifetech.type.OrderType
import com.realifetech.type.PaymentStatus
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class PaymentDataSourceTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient
    private lateinit var paymentDataSource: PaymentDataSource
    private lateinit var responseData: Response<GetMyPaymentIntentQuery.Data>
    private lateinit var getMyPaymentIntentSlot: CapturingSlot<ApolloCall.Callback<GetMyPaymentIntentQuery.Data>>
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        paymentDataSource = PaymentDataSource(apolloClient)
        responseData = mockk()
        getMyPaymentIntentSlot = CapturingSlot()

    }

    @Test
    fun `get My Payment Intent successfully`() = runBlocking {
        //Given
        every {
            responseData.data?.getMyPaymentIntent?.fragments?.paymentIntent
        } returns paymentIntent
        every {
            apolloClient.query(GetMyPaymentIntentQuery(PAYMENT_INTENT_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(getMyPaymentIntentSlot))
        } answers {
            getMyPaymentIntentSlot.captured.onResponse(responseData)
        }
        // When
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            // Then
            Assert.assertEquals(response, paymentIntent)
            print(response)
        }
    }

    @Test
    fun `get My Payment Intent results with throwable`() = runBlocking {
        //Given
        every {
            responseData.data
        } throws ApolloHttpException(any())
        every {
            apolloClient.query(GetMyPaymentIntentQuery(PAYMENT_INTENT_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(getMyPaymentIntentSlot))
        } answers {
            getMyPaymentIntentSlot.captured.onResponse(responseData)
        }
        // When
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get My Payment Intent results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.query(GetMyPaymentIntentQuery(PAYMENT_INTENT_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(getMyPaymentIntentSlot))
        } answers {
            getMyPaymentIntentSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            // Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get My Payment Intent results with null response`() = runBlocking {
        //Given
        every {
            apolloClient.query(GetMyPaymentIntentQuery(PAYMENT_INTENT_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(getMyPaymentIntentSlot))
        } answers {
            getMyPaymentIntentSlot.captured.onResponse(responseData)
        }
        val callback = { error: Exception?, response: PaymentIntent? ->
            // Then
            assert(error is Exception)
            Assert.assertSame(null,response)
        }
        // When
        //Null fragments
        every {
            responseData.data?.getMyPaymentIntent?.fragments
        } returns null
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            callback(error, response)
        }
        //Null paymentIntent
        every {
            responseData.data?.getMyPaymentIntent?.fragments?.paymentIntent
        } returns null
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            responseData.data?.getMyPaymentIntent
        } returns null
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            responseData.data
        } returns null
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            callback(error, response)
        }
    }

    companion object {
        private const val PAYMENT_INTENT_ID = "2"
    }
}