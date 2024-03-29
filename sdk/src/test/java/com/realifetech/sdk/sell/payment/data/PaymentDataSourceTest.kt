package com.realifetech.sdk.sell.payment.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.*
import com.realifetech.GetMyPaymentSourcesQuery.Data
import com.realifetech.fragment.PaymentIntent
import com.realifetech.sdk.core.data.model.payment.model.PaymentSource
import com.realifetech.sdk.core.data.model.payment.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.payment.mocks.PaymentIntentMocks.paymentIntent
import com.realifetech.sdk.sell.payment.mocks.PaymentIntentMocks.paymentIntentInput
import com.realifetech.sdk.sell.payment.mocks.PaymentIntentMocks.paymentIntentUpdateInput
import com.realifetech.sdk.sell.payment.mocks.PaymentSourcesMocks.emptyPaymentSources
import com.realifetech.sdk.sell.payment.mocks.PaymentSourcesMocks.paymentSource
import com.realifetech.sdk.sell.payment.mocks.PaymentSourcesMocks.paymentSourceInput
import com.realifetech.sdk.sell.payment.mocks.PaymentSourcesMocks.paymentSources
import com.realifetech.sdk.util.Constants
import com.realifetech.sdk.util.Constants.ERROR_MESSAGE
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class PaymentDataSourceTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient
    private lateinit var paymentDataSource: PaymentDataSourceImpl
    private lateinit var getPaymentData: Response<GetMyPaymentIntentQuery.Data>
    private lateinit var updatePaymentData: Response<UpdatePaymentIntentMutation.Data>
    private lateinit var createPaymentData: Response<CreateMyPaymentIntentMutation.Data>
    private lateinit var getPaymentSourcesData: Response<Data>
    private lateinit var deletePaymentSourceData: Response<DeleteMyPaymentSourceMutation.Data>
    private lateinit var addPaymentSourceData: Response<AddPaymentSourceToMyWalletMutation.Data>
    private lateinit var getPaymentIntentSlot: CapturingSlot<ApolloCall.Callback<GetMyPaymentIntentQuery.Data>>
    private lateinit var updatePaymentIntentSlot: CapturingSlot<ApolloCall.Callback<UpdatePaymentIntentMutation.Data>>
    private lateinit var createPaymentIntentSlot: CapturingSlot<ApolloCall.Callback<CreateMyPaymentIntentMutation.Data>>
    private lateinit var getPaymentSourcesSlot: CapturingSlot<ApolloCall.Callback<Data>>
    private lateinit var deletePaymentSourceSlot: CapturingSlot<ApolloCall.Callback<DeleteMyPaymentSourceMutation.Data>>
    private lateinit var addPaymentSourceSlot: CapturingSlot<ApolloCall.Callback<AddPaymentSourceToMyWalletMutation.Data>>


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        initMockedFields()
    }

    private fun initMockedFields() {
        paymentDataSource = PaymentDataSourceImpl(apolloClient)
        // init getPaymentIntent mocked response data & capture
        getPaymentData = mockk()
        getPaymentIntentSlot = CapturingSlot()
        // init updatePaymentIntent mocked response data & capture
        updatePaymentData = mockk()
        updatePaymentIntentSlot = CapturingSlot()
        // init createPaymentIntent mocked response data & capture
        createPaymentData = mockk()
        createPaymentIntentSlot = CapturingSlot()
        // init getPaymentSource mocked response data & capture
        getPaymentSourcesData = mockk()
        getPaymentSourcesSlot = CapturingSlot()
        // init deletePaymentIntent mocked response data & capture
        deletePaymentSourceData = mockk()
        deletePaymentSourceSlot = CapturingSlot()
        // init addPaymentIntent mocked response data & capture
        addPaymentSourceData = mockk()
        addPaymentSourceSlot = CapturingSlot()
    }

    @Test
    fun `get My Payment Intent successfully`() = runBlocking {
        //Given
        every {
            getPaymentData.data?.getMyPaymentIntent?.fragments?.paymentIntent
        } returns paymentIntent

        getPaymentIntentSuccessAnswer()
        // When
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            // Then
            assertEquals(null, error)
            assertEquals(paymentIntent, response)
        }
    }

    @Test
    fun `get My Payment Intent results with throwable`() = runBlocking {
        //Given
        every {
            getPaymentData.data
        } throws ApolloHttpException(any())

        getPaymentIntentSuccessAnswer()

        // When
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get My Payment Intent results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.query(GetMyPaymentIntentQuery(PAYMENT_INTENT_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(getPaymentIntentSlot))
        } answers {
            getPaymentIntentSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            // Then
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get My Payment Intent results with null response`() = runBlocking {
        //Given
        getPaymentIntentSuccessAnswer()
        val callback = { error: Exception?, response: PaymentIntent? ->
            // Then
            assertEquals(null, error)
            assertEquals(null, response)
        }
        // When
        //Null fragments
        every {
            getPaymentData.data?.getMyPaymentIntent?.fragments
        } returns null
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            callback(error, response)
        }
        //Null paymentIntent
        every {
            getPaymentData.data?.getMyPaymentIntent?.fragments?.paymentIntent
        } returns null
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            getPaymentData.data?.getMyPaymentIntent
        } returns null
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            getPaymentData.data
        } returns null
        paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            callback(error, response)
        }
    }

    private fun getPaymentIntentSuccessAnswer() {
        every {
            apolloClient.query(GetMyPaymentIntentQuery(PAYMENT_INTENT_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(getPaymentIntentSlot))
        } answers {
            getPaymentIntentSlot.captured.onResponse(getPaymentData)
        }
    }

    @Test
    fun `delete Payment Source successfully`() = runBlocking {
        //Given
        every {
            deletePaymentSourceData.data?.deleteMyPaymentSource?.fragments?.fragmentPaymentSource
        } returns paymentSource
        deletePaymentSourceSuccessAnswer()
        // When
        paymentDataSource.deleteMyPaymentSource(
            PAYMENT_INTENT_ID
        ) { error, response ->
            // Then
            assertEquals(null, error)
            assertEquals(response, paymentSource.asModel)
        }
    }

    @Test
    fun `delete Payment Source results with throwable`() = runBlocking {
        //Given
        every {
            deletePaymentSourceData.data
        } throws ApolloHttpException(any())
        deletePaymentSourceSuccessAnswer()
        // When
        paymentDataSource.deleteMyPaymentSource(
            PAYMENT_INTENT_ID
        ) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `delete Payment Source results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.mutate(
                DeleteMyPaymentSourceMutation(
                    PAYMENT_INTENT_ID
                )
            )
                .enqueue(capture(deletePaymentSourceSlot))
        } answers {
            deletePaymentSourceSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        paymentDataSource.deleteMyPaymentSource(
            PAYMENT_INTENT_ID
        ) { error, response ->
            // Then
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `delete Payment Source results with null response`() = runBlocking {
        // Given
        deletePaymentSourceSuccessAnswer()
        val callback = { error: Exception?, response: PaymentSource? ->
            assertEquals(null, error)
            assertEquals(null, response)
        }
        // When
        //Null fragments
        every {
            deletePaymentSourceData.data?.deleteMyPaymentSource?.fragments
        } returns null
        paymentDataSource.deleteMyPaymentSource(
            PAYMENT_INTENT_ID
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null FragmentPaymentSource
        every {
            deletePaymentSourceData.data?.deleteMyPaymentSource?.fragments?.fragmentPaymentSource
        } returns null
        paymentDataSource.deleteMyPaymentSource(
            PAYMENT_INTENT_ID
        ) { error, response ->
            // Then
            callback(error, response)
        }

        //Null getMyPaymentIntent
        every {
            deletePaymentSourceData.data?.deleteMyPaymentSource
        } returns null
        paymentDataSource.deleteMyPaymentSource(
            PAYMENT_INTENT_ID
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            deletePaymentSourceData.data
        } returns null
        paymentDataSource.deleteMyPaymentSource(
            PAYMENT_INTENT_ID
        ) { error, response ->
            // Then
            callback(error, response)
        }

    }

    private fun deletePaymentSourceSuccessAnswer() {
        every {
            apolloClient.mutate(
                DeleteMyPaymentSourceMutation(
                    PAYMENT_INTENT_ID
                )
            )
                .enqueue(capture(deletePaymentSourceSlot))
        } answers {
            deletePaymentSourceSlot.captured.onResponse(deletePaymentSourceData)
        }
    }

    @Test
    fun `update Payment Intent successfully`() = runBlocking {
        //Given
        every {
            updatePaymentData.data?.updateMyPaymentIntent?.fragments?.paymentIntent
        } returns paymentIntent
        updatePaymentIntentSuccessAnswer()
        // When
        paymentDataSource.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateInput
        ) { error, response ->
            // Then
            assertEquals(null, error)
            assertEquals(paymentIntent, response)
        }
    }


    @Test
    fun `update Payment Intent results with throwable`() = runBlocking {
        //Given
        every {
            updatePaymentData.data
        } throws ApolloHttpException(any())
        updatePaymentIntentSuccessAnswer()
        // When
        paymentDataSource.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateInput
        ) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `update Payment Intent results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.mutate(
                UpdatePaymentIntentMutation(
                    PAYMENT_INTENT_ID,
                    paymentIntentUpdateInput
                )
            )
                .enqueue(capture(updatePaymentIntentSlot))
        } answers {
            updatePaymentIntentSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        paymentDataSource.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateInput
        ) { error, response ->
            // Then
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `update Payment Intent results with null response`() = runBlocking {
        // Given
        updatePaymentIntentSuccessAnswer()
        val callback = { error: Exception?, response: PaymentIntent? ->

            assertSame(null, error)
            assertSame(null, response)
        }
        // When
        //Null fragments
        every {
            updatePaymentData.data?.updateMyPaymentIntent?.fragments
        } returns null
        paymentDataSource.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateInput
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null paymentIntent
        every {
            updatePaymentData.data?.updateMyPaymentIntent?.fragments?.paymentIntent
        } returns null
        paymentDataSource.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateInput
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            updatePaymentData.data?.updateMyPaymentIntent
        } returns null
        paymentDataSource.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateInput
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            updatePaymentData.data
        } returns null
        paymentDataSource.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateInput
        ) { error, response ->
            // Then
            callback(error, response)
        }

//        verifyNull(callback, updatePaymentData.data)
    }

    private fun updatePaymentIntentSuccessAnswer() {
        every {
            apolloClient.mutate(
                UpdatePaymentIntentMutation(
                    PAYMENT_INTENT_ID,
                    paymentIntentUpdateInput
                )
            )
                .enqueue(capture(updatePaymentIntentSlot))
        } answers {
            updatePaymentIntentSlot.captured.onResponse(updatePaymentData)
        }
    }

    @Test
    fun `create Payment Intent successfully`() = runBlocking {
        //Given
        every {
            createPaymentData.data?.createMyPaymentIntent?.fragments?.paymentIntent
        } returns paymentIntent
        createPaymentIntentSuccessAnswer()
        // When
        paymentDataSource.createPaymentIntent(
            paymentIntentInput
        ) { error, response ->
            // Then
            assertEquals(response, paymentIntent)
        }
    }

    @Test
    fun `create Payment Intent results with throwable`() = runBlocking {
        //Given
        every {
            createPaymentData.data
        } throws ApolloHttpException(any())
        createPaymentIntentSuccessAnswer()
        // When
        paymentDataSource.createPaymentIntent(
            paymentIntentInput
        ) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `create Payment Intent results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.mutate(
                CreateMyPaymentIntentMutation(
                    paymentIntentInput
                )
            )
                .enqueue(capture(createPaymentIntentSlot))
        } answers {
            createPaymentIntentSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        paymentDataSource.createPaymentIntent(
            paymentIntentInput
        ) { error, response ->
            // Then
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `create Payment Intent results with null response`() = runBlocking {
        // Given
        createPaymentIntentSuccessAnswer()
        val callback = { error: Exception?, response: PaymentIntent? ->
            assertEquals(null, error)
            assertSame(null, response)
        }
        // When
        //Null fragments
        every {
            createPaymentData.data?.createMyPaymentIntent?.fragments
        } returns null
        paymentDataSource.createPaymentIntent(
            paymentIntentInput
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null paymentIntent
        every {
            createPaymentData.data?.createMyPaymentIntent?.fragments?.paymentIntent
        } returns null
        paymentDataSource.createPaymentIntent(
            paymentIntentInput
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            createPaymentData.data?.createMyPaymentIntent
        } returns null
        paymentDataSource.createPaymentIntent(
            paymentIntentInput
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            createPaymentData.data
        } returns null
        paymentDataSource.createPaymentIntent(
            paymentIntentInput
        ) { error, response ->
            // Then
            callback(error, response)
        }

//        verifyNull(callback, updatePaymentData.data)
    }

    private fun createPaymentIntentSuccessAnswer() {
        every {
            apolloClient.mutate(
                CreateMyPaymentIntentMutation(
                    paymentIntentInput
                )
            )
                .enqueue(capture(createPaymentIntentSlot))
        } answers {
            createPaymentIntentSlot.captured.onResponse(createPaymentData)
        }
    }

    @Test
    fun `add Payment Source successfully`() = runBlocking {
        //Given
        every {
            addPaymentSourceData.data?.addPaymentSourceToMyWallet?.fragments?.fragmentPaymentSource
        } returns paymentSource
        addPaymentSourceSuccessAnswer()
        // When
        paymentDataSource.addPaymentSource(
            paymentSourceInput
        ) { error, response ->
            // Then
            assertEquals(response, paymentSource.asModel)
        }
    }

    @Test
    fun `add Payment Source results with throwable`() = runBlocking {
        //Given
        every {
            addPaymentSourceData.data
        } throws ApolloHttpException(any())
        addPaymentSourceSuccessAnswer()
        // When
        paymentDataSource.addPaymentSource(
            paymentSourceInput
        ) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `add Payment Source results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.mutate(
                AddPaymentSourceToMyWalletMutation(
                    paymentSourceInput
                )
            )
                .enqueue(capture(addPaymentSourceSlot))
        } answers {
            addPaymentSourceSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        paymentDataSource.addPaymentSource(
            paymentSourceInput
        ) { error, response ->
            // Then
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `add Payment Source results with null response`() = runBlocking {
        // Given
        addPaymentSourceSuccessAnswer()
        val callback = { error: Exception?, response: PaymentSource? ->
            assertEquals(null, error)
            assertEquals(null, response)
        }
        // When
        //Null fragments
        every {
            addPaymentSourceData.data?.addPaymentSourceToMyWallet?.fragments
        } returns null
        paymentDataSource.addPaymentSource(
            paymentSourceInput
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null FragmentPaymentSource
        every {
            addPaymentSourceData.data?.addPaymentSourceToMyWallet?.fragments?.fragmentPaymentSource
        } returns null
        paymentDataSource.addPaymentSource(
            paymentSourceInput
        ) { error, response ->
            // Then
            callback(error, response)
        }

        //Null getMyPaymentIntent
        every {
            addPaymentSourceData.data?.addPaymentSourceToMyWallet
        } returns null
        paymentDataSource.addPaymentSource(
            paymentSourceInput
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            addPaymentSourceData.data
        } returns null
        paymentDataSource.addPaymentSource(
            paymentSourceInput
        ) { error, response ->
            // Then
            callback(error, response)
        }

    }

    private fun addPaymentSourceSuccessAnswer(errors: List<Error>? = null) {
        every { addPaymentSourceData.errors } returns errors
        every {
            apolloClient.mutate(AddPaymentSourceToMyWalletMutation(paymentSourceInput))
                .enqueue(capture(addPaymentSourceSlot))
        } answers {
            addPaymentSourceSlot.captured.onResponse(addPaymentSourceData)
        }
    }

    @Test
    fun `get Payment Sources successfully`() = runBlocking {
        //Given
        every {
            getPaymentSourcesData.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.edges
        } returns paymentSources
        every {
            getPaymentSourcesData.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.nextPage
        } returns NEXT_PAGE
        getPaymentSourceSuccessAnswer()
        // When
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE, PAGE
        ) { error, response ->
            // Then
            assertEquals(
                response, PaginatedObject(
                    paymentSources.map { it.asModel },
                    NEXT_PAGE
                )
            )
        }
    }

    @Test
    fun `get Payment Sources results with throwable`() = runBlocking {
        //Given
        every {
            getPaymentSourcesData.data
        } throws ApolloHttpException(any())
        getPaymentSourceSuccessAnswer()
        // When
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE,
            PAGE
        ) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Payment Sources results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.query(
                GetMyPaymentSourcesQuery(
                    PAGE_SIZE,
                    Input.fromNullable(PAGE)
                )
            ).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                .build()
                .enqueue(capture(getPaymentSourcesSlot))
        } answers {
            getPaymentSourcesSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE,
            PAGE
        ) { error, response ->
            // Then
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Payment Sources results with null response`() = runBlocking {
        // Given
        getPaymentSourceSuccessAnswer()
        val callback = { error: Exception?, response: PaginatedObject<PaymentSource?>? ->
            assertEquals(null, error)
            assertEquals(null, response)
        }
        // When
        //Null Edges
        every {
            getPaymentSourcesData.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.nextPage
        } returns null
        //Null fragments
        every {
            getPaymentSourcesData.data?.getMyPaymentSources?.fragments
        } returns null
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE,
            PAGE
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null PaymentSourceEdge
        every {
            getPaymentSourcesData.data?.getMyPaymentSources?.fragments?.paymentSourceEdge
        } returns null
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE,
            PAGE
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null Edges
        every {
            getPaymentSourcesData.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.edges
        } returns emptyPaymentSources
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE,
            PAGE
        ) { error, response ->
            // Then
            assertEquals(
                PaginatedObject(
                    emptyPaymentSources.map { it?.asModel },
                    null
                ), response
            )
            assertEquals(null, error)
        }
        every {
            getPaymentSourcesData.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.edges
        } returns null
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE,
            PAGE
        ) { error, response ->
            // Then
            assertEquals(
                PaginatedObject<PaymentSource?>(
                    null,
                    null
                ), response
            )
            assertEquals(null, error)
        }
        //Null getMyPaymentSources
        every {
            getPaymentSourcesData.data?.getMyPaymentSources
        } returns null
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE,
            PAGE
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Null getMyPaymentIntent
        every {
            getPaymentSourcesData.data
        } returns null
        paymentDataSource.getMyPaymentSources(
            PAGE_SIZE,
            PAGE
        ) { error, response ->
            // Then
            callback(error, response)
        }

    }

    private fun getPaymentSourceSuccessAnswer() {
        every {
            apolloClient.query(GetMyPaymentSourcesQuery(PAGE_SIZE, Input.fromNullable(PAGE)))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                .build()
                .enqueue(capture(getPaymentSourcesSlot))
        } answers {
            getPaymentSourcesSlot.captured.onResponse(getPaymentSourcesData)
        }
    }

    companion object {
        private const val PAYMENT_INTENT_ID = "2"
        private const val PAGE_SIZE = 10
        private const val PAGE = 2
        private const val NEXT_PAGE = 3

    }
}