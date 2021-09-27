package com.realifetech.sdk.core.data.model.fulfilmentPoint

import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks
import junit.framework.Assert.assertEquals
import org.junit.Test

class FulfilmentPointExtensionsKtTest {


    @Test
    fun `get Title from fulfilment Point returns title`() {
        val title = FFPMocks.fulfilmentPoint?.title
        assertEquals("Meal Deal", title)
    }

    @Test
    fun `get Title from fulfilment Point returns empty`() {
        val title = FFPMocks.fulfilmentPointWithNullArguments.title
        assertEquals(EMPTY, title)
    }

    @Test
    fun `get Description from fulfilment Point returns description`() {
        val description = FFPMocks.fulfilmentPoint?.description
        assertEquals("Batata and Chicken", description)
    }

    @Test
    fun `get Description from fulfilment Point returns empty`() {
        val description = FFPMocks.fulfilmentPointWithNullArguments.description
        assertEquals(EMPTY, description)
    }

    @Test
    fun `get Name from fulfilment Point returns name`() {
        val name = FFPMocks.fulfilmentPointCategory.name
        assertEquals("Batman vs Superman", name)
    }

    @Test
    fun `get Name from fulfilment Point returns empty`() {
        val name = FFPMocks.fulfilmentPointCategoryWithEmptyArguments.name
        assertEquals(EMPTY, name)
    }


    @Test
    fun `get Category from FulfilmentPoint returns category`() {
        val category = FFPMocks.fragmentFulfilmentPoint.categories?.first()?.asModel
        assertEquals(FFPMocks.fulfilmentPointCategory, category)
    }

    @Test
    fun `get SeatForm from FulfilmentPoint returns SeatForm`() {
        val form = FFPMocks.fulfilmentPoint?.form
        assertEquals(FFPMocks.form, form)
    }


    companion object {
        private const val EMPTY = ""
    }
}