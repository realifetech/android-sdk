package com.realifetech.sdk.sell.basket.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.*
import com.realifetech.sdk.core.data.model.basket.Basket
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.sell.basket.mocks.BasketMocks
import com.realifetech.sdk.sell.order.mocks.OrdersMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

@ExperimentalCoroutinesApi
class BasketDataSourceImplTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient

    private lateinit var getMyBasketData: Response<GetMyBasketQuery.Data>
    private lateinit var createMyBasketData: Response<CreateMyBasketMutation.Data>
    private lateinit var updateMyBasketData: Response<UpdateMyBasketMutation.Data>
    private lateinit var checkoutMyBasketData: Response<CheckoutMyBasketMutation.Data>
    private lateinit var deleteMyBasketData: Response<DeleteMyBasketMutation.Data>
    private lateinit var getBasketSlot: CapturingSlot<ApolloCall.Callback<GetMyBasketQuery.Data>>
    private lateinit var createBasketSlot: CapturingSlot<ApolloCall.Callback<CreateMyBasketMutation.Data>>
    private lateinit var checkoutBasketSlot: CapturingSlot<ApolloCall.Callback<CheckoutMyBasketMutation.Data>>
    private lateinit var updateBasketSlot: CapturingSlot<ApolloCall.Callback<UpdateMyBasketMutation.Data>>
    private lateinit var deleteBasketSlot: CapturingSlot<ApolloCall.Callback<DeleteMyBasketMutation.Data>>
    private lateinit var basketDataSource: BasketDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        initMockedFields()
    }

    private fun initMockedFields() {
        basketDataSource = BasketDataSourceImpl(apolloClient)
        getMyBasketData = mockk()
        createMyBasketData = mockk()
        updateMyBasketData = mockk()
        deleteMyBasketData = mockk()
        checkoutMyBasketData = mockk()
        getBasketSlot = CapturingSlot()
        createBasketSlot = CapturingSlot()
        updateBasketSlot = CapturingSlot()
        checkoutBasketSlot = CapturingSlot()
        deleteBasketSlot = CapturingSlot()
    }

    @Test
    fun `get my basket returns Data`() = runBlockingTest {
        //Given
        every {
            getMyBasketData.data?.getMyBasket?.fragments?.fragmentBasket
        } returns BasketMocks.basketFragment
        getBasketSuccessAnswer()
        // When
        basketDataSource.getBasket { error, response ->
            // Then
            Assert.assertEquals(BasketMocks.basket, response)
            Assert.assertEquals(null, error)

        }

    }

    @Test
    fun `get my basket returns Exception`() = runBlockingTest {
        //Given
        every {
            getMyBasketData.data?.getMyBasket?.fragments?.fragmentBasket
        } throws ApolloHttpException(any())

        getBasketSuccessAnswer()
        // When
        basketDataSource.getBasket { error, response ->
            //Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get my basket returns Failure`() = runBlockingTest {
        //Given
        every {
            apolloClient.query(GetMyBasketQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(getBasketSlot))
        } answers {
            getBasketSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        basketDataSource.getBasket { error, response ->
            //Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)

        }
    }

    private fun getBasketSuccessAnswer() {
        every {
            apolloClient.query(GetMyBasketQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(getBasketSlot))
        } answers {
            getBasketSlot.captured.onResponse(getMyBasketData)
        }
    }

    @Test
    fun `create My Basket returns Data`() = runBlockingTest {
        //Given
        every {
            createMyBasketData.data?.createMyBasket?.fragments?.fragmentBasket
        } returns BasketMocks.basketFragment

        createBasketSuccessAnswer()
        // When
        basketDataSource.createMyBasket(BasketMocks.basketRequestInput) { error, response ->
            // Then
            Assert.assertEquals(BasketMocks.basket, response)
            Assert.assertEquals(null, error)

        }
    }

    @Test
    fun `create My Basket returns null`() = runBlockingTest {
        //Given
        createBasketSuccessAnswer()
        val callback = { error: Exception?, response: Basket? ->
            // Then
            Assert.assertEquals(null, response)
            Assert.assertEquals(null, error)

        }
        every {
            createMyBasketData.data?.createMyBasket?.fragments?.fragmentBasket
        } returns null
        // When
        basketDataSource.createMyBasket(BasketMocks.basketRequestInput, callback)
        every {
            createMyBasketData.data?.createMyBasket?.fragments
        } returns null
        // When
        basketDataSource.createMyBasket(BasketMocks.basketRequestInput, callback)

        every {
            createMyBasketData.data?.createMyBasket
        } returns null
        // When
        basketDataSource.createMyBasket(BasketMocks.basketRequestInput, callback)
        every {
            createMyBasketData.data
        } returns null
        // When
        basketDataSource.createMyBasket(BasketMocks.basketRequestInput, callback)
    }

    @Test
    fun `create My Basket returns  Exception`() = runBlockingTest {
        //Given
        every {
            createMyBasketData.data?.createMyBasket?.fragments?.fragmentBasket
        } throws ApolloHttpException(any())

        createBasketSuccessAnswer()
        // When
        basketDataSource.createMyBasket(BasketMocks.basketRequestInput) { error, response ->
            // Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }

    }

    @Test
    fun `create my basket returns Failure`() = runBlockingTest {
        //Given
        every {
            apolloClient.mutate(CreateMyBasketMutation(BasketMocks.basketRequestInput.toInput()))
                .enqueue(capture(createBasketSlot))
        } answers {
            createBasketSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        basketDataSource.createMyBasket(BasketMocks.basketRequestInput) { error, response ->
            //Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }
    }

    private fun createBasketSuccessAnswer() {
        every {
            apolloClient.mutate(CreateMyBasketMutation(BasketMocks.basketRequestInput.toInput()))
                .enqueue(capture(createBasketSlot))
        } answers {
            createBasketSlot.captured.onResponse(createMyBasketData)
        }
    }

    @Test
    fun `checkout My Basket property returns Data`() = runBlockingTest {
        //Given
        every {
            checkoutMyBasketData.data?.checkoutMyBasket?.fragments?.fragmentOrder
        } returns OrdersMocks.order1

        checkoutBasketSuccessAnswer()
        // When
        basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput) { error, response ->
            // Then
            Assert.assertEquals(OrdersMocks.order1.asModel, response)
            Assert.assertEquals(null, error)
        }

    }

    @Test
    fun `checkout My Basket returns null`() = runBlockingTest {
        //Given
        checkoutBasketSuccessAnswer()
        val callback = { error: Exception?, response: Order? ->
            // Then
            Assert.assertEquals(null, response)
            Assert.assertEquals(null, error)

        }
        every {
            checkoutMyBasketData.data?.checkoutMyBasket?.fragments?.fragmentOrder
        } returns null
        // When
        basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput, callback)
        every {
            checkoutMyBasketData.data?.checkoutMyBasket?.fragments
        } returns null
        // When
        basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput, callback)

        every {
            checkoutMyBasketData.data?.checkoutMyBasket
        } returns null
        // When
        basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput, callback)
        every {
            checkoutMyBasketData.data
        } returns null
        // When
        basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput, callback)
    }


    @Test
    fun `checkout My Basket property returns  Exception`() = runBlockingTest {
        //Given
        every {
            checkoutMyBasketData.data?.checkoutMyBasket?.fragments?.fragmentOrder
        } throws ApolloHttpException(any())

        checkoutBasketSuccessAnswer()
        // When
        basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput) { error, response ->
            // Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }

    }

    @Test
    fun `checkout My Basket property returns  Failure`() = runBlockingTest {
        //Given
        every {
            apolloClient.mutate(CheckoutMyBasketMutation(BasketMocks.checkoutRequestInput.toInput()))
                .enqueue(capture(checkoutBasketSlot))
        } answers {
            checkoutBasketSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput) { error, response ->
            // Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }

    }

    private fun checkoutBasketSuccessAnswer() {
        every {
            apolloClient.mutate(CheckoutMyBasketMutation(BasketMocks.checkoutRequestInput.toInput()))
                .enqueue(capture(checkoutBasketSlot))
        } answers {
            checkoutBasketSlot.captured.onResponse(checkoutMyBasketData)
        }
    }

    @Test
    fun `update My Basket property returns Data`() = runBlockingTest {
        //Given
        every {
            updateMyBasketData.data?.updateMyBasket?.fragments?.fragmentBasket
        } returns BasketMocks.basketFragment

        updateBasketSuccessAnswer()
        // When
        basketDataSource.updateMyBasket(BasketMocks.basketRequestInput) { error, response ->
            // Then
            Assert.assertEquals(BasketMocks.basket, response)
            Assert.assertEquals(null, error)
        }

    }

    private fun updateBasketSuccessAnswer() {
        every {
            apolloClient.mutate(UpdateMyBasketMutation(Input.optional(BasketMocks.basketRequestInput)))
                .enqueue(capture(updateBasketSlot))
        } answers {
            updateBasketSlot.captured.onResponse(updateMyBasketData)
        }

    }

    @Test
    fun `update My Basket property returns Exception`() = runBlockingTest {
        //Given
        every {
            updateMyBasketData.data?.updateMyBasket?.fragments?.fragmentBasket
        } throws ApolloHttpException(any())

        updateBasketSuccessAnswer()
        // When
        basketDataSource.updateMyBasket(BasketMocks.basketRequestInput) { error, response ->
            // Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }

    }

    @Test
    fun `update My Basket property returns Failure`() = runBlockingTest {
        //Given
        every {
            apolloClient.mutate(UpdateMyBasketMutation(Input.optional(BasketMocks.basketRequestInput)))
                .enqueue(capture(updateBasketSlot))
        } answers {
            updateBasketSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        basketDataSource.updateMyBasket(BasketMocks.basketRequestInput) { error, response ->
            // Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }

    }

    @Test
    fun `update My Basket returns null`() = runBlockingTest {
        //Given
        updateBasketSuccessAnswer()
        val callback = { error: Exception?, response: Basket? ->
            // Then
            Assert.assertEquals(null, response)
            Assert.assertEquals(null, error)

        }
        every {
            updateMyBasketData.data?.updateMyBasket?.fragments?.fragmentBasket
        } returns null
        // When
        basketDataSource.updateMyBasket(BasketMocks.basketRequestInput, callback)
        every {
            updateMyBasketData.data?.updateMyBasket?.fragments
        } returns null
        // When
        basketDataSource.updateMyBasket(BasketMocks.basketRequestInput, callback)

        every {
            updateMyBasketData.data?.updateMyBasket
        } returns null
        // When
        basketDataSource.updateMyBasket(BasketMocks.basketRequestInput, callback)
        every {
            updateMyBasketData.data
        } returns null
        // When
        basketDataSource.updateMyBasket(BasketMocks.basketRequestInput, callback)
    }


    @Test
    fun `delete My Basket property returns Data`() = runBlockingTest {
        //Given

        every {
            deleteMyBasketData.data?.deleteMyBasket?.message
        } returns BasketMocks.response.message
        deleteBasketSuccessAnswer()
        // When
        basketDataSource.deleteMyBasket { error, response ->
            // Then
            println(response)
            Assert.assertEquals(BasketMocks.response, response)
            Assert.assertEquals(null, error)
        }
    }

    private fun deleteBasketSuccessAnswer() {
        every {
            apolloClient.mutate(DeleteMyBasketMutation())
                .enqueue(capture(deleteBasketSlot))
        } answers {
            deleteBasketSlot.captured.onResponse(deleteMyBasketData)
        }


    }

    @Test
    fun `delete My Basket property returns  Exception`() = runBlockingTest {
        //Given
        every {
            deleteMyBasketData.data?.deleteMyBasket?.message
        } throws ApolloHttpException(any())

        deleteBasketSuccessAnswer()
        // When

        basketDataSource.deleteMyBasket { error, response ->
            // Then
            Assert.assertEquals(null, response)
            Assert.assertEquals(null, error)
        }
    }

    @Test
    fun `delete My Basket property returns  Failure`() = runBlockingTest {
        //Given
        every {
            val response = apolloClient.mutate(DeleteMyBasketMutation())
            response.enqueue(capture(deleteBasketSlot))
        } answers {
            deleteBasketSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        basketDataSource.deleteMyBasket { error, response ->
            // Then
            println(response)
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }

    }
}