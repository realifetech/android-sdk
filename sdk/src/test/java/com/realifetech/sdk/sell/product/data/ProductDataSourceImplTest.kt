package com.realifetech.sdk.sell.product.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetProductByIdQuery
import com.realifetech.GetProductsQuery
import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.core.data.model.shared.`object`.asInput
import com.realifetech.sdk.sell.product.mocks.ProductMocks
import com.realifetech.sdk.sell.product.mocks.ProductMocks.PAGE
import com.realifetech.sdk.sell.product.mocks.ProductMocks.PAGE_SIZE
import com.realifetech.sdk.sell.product.mocks.ProductMocks.filters
import com.realifetech.sdk.sell.product.mocks.ProductMocks.params
import com.realifetech.sdk.sell.product.mocks.ProductMocks.productId
import com.realifetech.type.ProductFilter
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any

class ProductDataSourceImplTest {

    @RelaxedMockK
    lateinit var apolloClient: ApolloClient
    private lateinit var productDataSource: ProductDataSourceImpl
    private lateinit var productByIdData: Response<GetProductByIdQuery.Data>
    private lateinit var productByIdSlot: CapturingSlot<ApolloCall.Callback<GetProductByIdQuery.Data>>
    private lateinit var productsData: Response<GetProductsQuery.Data>
    private lateinit var productsSlot: CapturingSlot<ApolloCall.Callback<GetProductsQuery.Data>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        productDataSource = ProductDataSourceImpl(apolloClient)
        initMockedFields()
    }

    private fun initMockedFields() {
        productByIdData = mockk()
        productByIdSlot = slot()
        productsData = mockk()
        productsSlot = slot()

    }

    @Test
    fun `get products returns data`() = runBlockingTest {
        // Given
        every { productsData.data?.getProducts } returns ProductMocks.products
        //When
        getProductsCall(filters = filters, params = params)
        //Then
        productDataSource.getProducts(
            PAGE_SIZE, PAGE, filters, params
        ) { error, response ->
            assertEquals(null, error)
            assertEquals(ProductMocks.paginatedObject, response)
        }

    }
    @Test
    fun `get products returns data with null params`() = runBlockingTest {
        // Given
        every { productsData.data?.getProducts } returns ProductMocks.products
        //When
        getProductsCall(filters = filters, params = null)
        //Then
        productDataSource.getProducts(
            PAGE_SIZE, PAGE, filters, null
        ) { error, response ->
            assertEquals(null, error)
            assertEquals(ProductMocks.paginatedObject, response)
        }

    }

    @Test
    fun `get products throws exception`() {
        // Given
        every { productsData.data?.getProducts } throws ApolloHttpException(
            any()
        )
        //When
        getProductsCall(filters = filters, params = params)
        //Then
        productDataSource.getProducts(
            PAGE_SIZE, PAGE, filters, params
        ) { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }

    }

    @Test
    fun `get products returns failure`() {
        // Given
        every { productsData.data?.getProducts } throws ApolloHttpException(
            any()
        )
        //When
        getProductsCall(true, filters = filters, params = params)
        //Then
        productDataSource.getProducts(
            PAGE_SIZE, PAGE, filters, params
        ) { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get products returns null`() {
        // Given
        val callback = { error: Exception?, response: PaginatedObject<Product?>? ->
            assertEquals(null, error)
            assertEquals(PaginatedObject<Product>(items=null, nextPage=null), response)
        }
        getProductsCall(filters = filters, params = params)
        every { productsData.data?.getProducts } returns null
        //When
        productDataSource.getProducts(
            PAGE_SIZE, PAGE, filters, params
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }

        every { productsData.data } returns null
        //When
        productDataSource.getProducts(
            PAGE_SIZE, PAGE, filters, params
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }

    }

    private fun getProductsCall(
        shouldFail: Boolean = false,
        filters: ProductFilter,
        params: List<FilterParamWrapper>?
    ) {
        every {
            apolloClient.query(
                GetProductsQuery(
                    PAGE_SIZE, PAGE.toInput(), filters.toInput(),
                    Input.optional(params?.map { it.asInput })
                )
            )
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(productsSlot))
        } answers {
            if (shouldFail) {
                productsSlot.captured.onFailure(ApolloException(""))
            } else {
                productsSlot.captured.onResponse(productsData)
            }
        }
    }

    @Test
    fun `get Product By Id returns data`() = runBlockingTest {
        // Given
        every { productByIdData.data?.getProduct?.fragments?.fragmentProduct } returns ProductMocks.fragmentProduct
        getProductByIdCall(params)
        //When
        productDataSource.getProductById(
            productId,
            params
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(ProductMocks.product, response)
        }

    }

    @Test
    fun `get Product By Id returns data with null params`() = runBlockingTest {
        // Given
        every { productByIdData.data?.getProduct?.fragments?.fragmentProduct } returns ProductMocks.fragmentProduct
        getProductByIdCall(null)
        //When
        productDataSource.getProductById(
            productId,
            null
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(ProductMocks.product, response)
        }

    }


    @Test
    fun `get Product By Id throws exception`() {
        // Given
        every { productByIdData.data?.getProduct?.fragments?.fragmentProduct } throws ApolloHttpException(
            any()
        )
        //When
        getProductByIdCall(params)
        //Then
        productDataSource.getProductById(
            productId,
            params
        ) { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }

    }

    @Test
    fun `get Product By Id returns failure`() {
        // Given
        every { productByIdData.data?.getProduct?.fragments?.fragmentProduct } throws ApolloHttpException(
            any()
        )
        //When
        getProductByIdCall(params, true)
        //Then
        productDataSource.getProductById(
            productId,
            params
        ) { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Product By Id returns null`() {
        // Given
        val callback = { error: Exception?, response: Product? ->
            assertEquals(null, error)
            assertEquals(null, response)
        }
        getProductByIdCall(params)
        every { productByIdData.data?.getProduct?.fragments?.fragmentProduct } returns null
        //When
        productDataSource.getProductById(
            productId,
            params
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }

        every { productByIdData.data?.getProduct?.fragments } returns null
        //When
        productDataSource.getProductById(
            productId,
            params
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { productByIdData.data?.getProduct } returns null
        //When
        productDataSource.getProductById(
            productId,
            params
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { productByIdData.data } returns null
        //When
        productDataSource.getProductById(
            productId,
            params
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
    }

    private fun getProductByIdCall(params: List<FilterParamWrapper>?, shouldFail: Boolean = false) {
        every {
            apolloClient.query(
                GetProductByIdQuery(
                    productId, Input.optional(params?.map { it.asInput })
                )
            )
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(productByIdSlot))
        } answers {
            if (shouldFail) {
                productByIdSlot.captured.onFailure(ApolloException(""))
            } else {
                productByIdSlot.captured.onResponse(productByIdData)
            }
        }
    }

}