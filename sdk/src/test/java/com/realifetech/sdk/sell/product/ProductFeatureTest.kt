package com.realifetech.sdk.sell.product

import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.product.domain.ProductRepository
import com.realifetech.sdk.sell.product.mocks.ProductMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductFeatureTest {

    @RelaxedMockK
    lateinit var productRepository: ProductRepository
    private lateinit var productFeature: ProductFeature
    private lateinit var productsSlot: CapturingSlot<(error: Exception?, response: PaginatedObject<Product?>?) -> Unit>
    private lateinit var productSlot: CapturingSlot<(error: Exception?, response: Product?) -> Unit>

    @Before

    fun setUp() {
        MockKAnnotations.init(this)
        productFeature = ProductFeature(productRepository)
        productsSlot = slot()
        productSlot = slot()
    }


    @Test
    fun `get Products results data`() {
        //Given
        every {
            productRepository.getProducts(
                ProductMocks.PAGE_SIZE,
                ProductMocks.PAGE,
                ProductMocks.filters, ProductMocks.params,
                capture(productsSlot)
            )
        }
            .answers { productsSlot.captured.invoke(null, ProductMocks.paginatedObject) }
        //When
        productFeature.getProducts(
            ProductMocks.PAGE_SIZE,
            ProductMocks.PAGE,
            ProductMocks.filters, ProductMocks.params,
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(ProductMocks.paginatedObject, response)

        }
    }

    @Test
    fun `get Products results error`() {
        //Given
        every {
            productRepository.getProducts(
                ProductMocks.PAGE_SIZE,
                ProductMocks.PAGE,
                ProductMocks.filters, ProductMocks.params, capture(productsSlot)
            )
        }
            .answers { productsSlot.captured.invoke(Exception(""), null) }
        //When
        productFeature.getProducts(
            ProductMocks.PAGE_SIZE,
            ProductMocks.PAGE,
            ProductMocks.filters, ProductMocks.params,
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
            productRepository.getProductById(
                ProductMocks.productId, ProductMocks.params,
                capture(productSlot)
            )
        }
            .answers { productSlot.captured.invoke(null, ProductMocks.product) }
        //When
        productFeature.getProductById(
            ProductMocks.productId, ProductMocks.params
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(ProductMocks.product, response)

        }
    }

    @Test
    fun `get Product By Id results error`() {
        //Given
        every {
            productRepository.getProductById(
                ProductMocks.productId, ProductMocks.params,
                capture(productSlot)
            )
        }
            .answers { productSlot.captured.invoke(Exception(""), null) }
        //When
        productFeature.getProductById(
            ProductMocks.productId, ProductMocks.params
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }
}