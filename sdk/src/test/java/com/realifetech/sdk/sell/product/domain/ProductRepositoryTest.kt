package com.realifetech.sdk.sell.product.domain

import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.product.data.ProductDataSource
import com.realifetech.sdk.sell.product.mocks.ProductMocks
import com.realifetech.sdk.sell.product.mocks.ProductMocks.filters
import com.realifetech.sdk.sell.product.mocks.ProductMocks.paginatedObject
import com.realifetech.sdk.sell.product.mocks.ProductMocks.params
import com.realifetech.sdk.sell.product.mocks.ProductMocks.product
import com.realifetech.sdk.sell.product.mocks.ProductMocks.productId
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {


    @RelaxedMockK
    lateinit var productsDataSource: ProductDataSource
    private lateinit var productRepository: ProductRepository
    private lateinit var productsSlot: CapturingSlot<(error: Exception?, response: PaginatedObject<Product?>?) -> Unit>
    private lateinit var productSlot: CapturingSlot<(error: Exception?, response: Product?) -> Unit>

    @Before

    fun setUp() {
        MockKAnnotations.init(this)
        productRepository = ProductRepository(productsDataSource)
        productsSlot = slot()
        productSlot = slot()
    }


    @Test
    fun `get Products results data`() {
        //Given
        every {
            productsDataSource.getProducts(
                ProductMocks.PAGE_SIZE,
                ProductMocks.PAGE,
                filters, params,
                capture(productsSlot)
            )
        }
            .answers { productsSlot.captured.invoke(null, paginatedObject) }
        //When
        productRepository.getProducts(
            ProductMocks.PAGE_SIZE,
            ProductMocks.PAGE,
            filters, params,
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(paginatedObject, response)

        }
    }

    @Test
    fun `get Products results error`() {
        //Given
        every {
            productsDataSource.getProducts(
                ProductMocks.PAGE_SIZE,
                ProductMocks.PAGE,
                filters, params, capture(productsSlot)
            )
        }
            .answers { productsSlot.captured.invoke(Exception(""), null) }
        //When
        productRepository.getProducts(
            ProductMocks.PAGE_SIZE,
            ProductMocks.PAGE,
            filters, params,
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }

    @Test
    fun `get Product By Id results data`() {
        //Given
        every {
            productsDataSource.getProductById(
                productId, params,
                capture(productSlot)
            )
        }
            .answers { productSlot.captured.invoke(null, product) }
        //When
        productRepository.getProductById(
            productId, params
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(product, response)

        }
    }

    @Test
    fun `get Product By Id results error`() {
        //Given
        every {
            productsDataSource.getProductById(
                productId, params,
                capture(productSlot)
            )
        }
            .answers { productSlot.captured.invoke(Exception(""), null) }
        //When
        productRepository.getProductById(
            productId, params
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }
}