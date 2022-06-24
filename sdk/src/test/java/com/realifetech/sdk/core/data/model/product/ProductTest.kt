package com.realifetech.sdk.core.data.model.product

import com.realifetech.sdk.core.data.model.fulfilmentPoint.asModel
import com.realifetech.sdk.sell.product.mocks.ProductMocks.fragmentProduct
import com.realifetech.sdk.sell.product.mocks.ProductMocks.product
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductTest {

    @Test
    fun getId() {
        assertEquals(fragmentProduct.id, product.id)
    }
    @Test
    fun getExternalId() {
        assertEquals(fragmentProduct.externalId, product.externalId)
    }

    @Test
    fun getStatus() {
        assertEquals(fragmentProduct.status, product.status)
    }

    @Test
    fun getImages() {

        assertEquals(listOf(ProductImage("url", 1)), product.images)
    }

    @Test
    fun getTranslations() {
        assertEquals(listOf(ProductTranslation("", "en", "", "")), product.translations)
    }

    @Test
    fun getFulfilmentPoints() {
        assertEquals(
            fragmentProduct.fulfilmentPoints?.map { it?.fragments?.fragmentFulfilmentPoint?.asModel },
            product.fulfilmentPoints
        )
    }

    @Test
    fun getCategories() {
        assertEquals(fragmentProduct.categories?.map { it?.asModel }, product.categories)
    }

    @Test
    fun getVariants() {
        assertEquals(
            fragmentProduct.variants?.map { it?.fragments?.productVariant?.asModel },
            product.variants
        )
    }

    @Test
    fun getModifierLists() {
        assertEquals(fragmentProduct.modifierLists?.map { it?.asModel }, product.modifierLists)
    }

    @Test
    fun getReference() {
        assertEquals(fragmentProduct.reference, product.reference)
    }
}