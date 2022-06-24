package com.realifetech.sdk.sell.payment

import com.realifetech.sdk.core.data.model.payment.model.PaymentSource
import com.realifetech.sdk.core.data.model.payment.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.payment.domain.PaymentRepository
import com.realifetech.fragment.PaymentIntent
import com.realifetech.sdk.sell.payment.mocks.PaymentIntentMocks.paymentIntent
import com.realifetech.sdk.sell.payment.mocks.PaymentIntentMocks.paymentIntentUpdateWrapper
import com.realifetech.sdk.sell.payment.mocks.PaymentIntentMocks.paymentIntentWrapper
import com.realifetech.sdk.sell.payment.mocks.PaymentSourcesMocks.paymentSource
import com.realifetech.sdk.sell.payment.mocks.PaymentSourcesMocks.paymentSourceWrapper
import com.realifetech.sdk.sell.payment.mocks.PaymentSourcesMocks.paymentSources
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PaymentFeatureTest {

    @RelaxedMockK
    private lateinit var paymentRepository: PaymentRepository
    private lateinit var paymentIntentSlot: CapturingSlot<(error: Exception?, response: PaymentIntent?) -> Unit>
    private lateinit var paymentSourceSlot: CapturingSlot<(error: Exception?, response: PaymentSource?) -> Unit>
    private lateinit var paginatedObjectSlot: CapturingSlot<(error: Exception?, response: PaginatedObject<PaymentSource?>?) -> Unit>
    private lateinit var paymentFeature: PaymentFeature

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        paymentFeature = PaymentFeature(paymentRepository)
        paymentIntentSlot = CapturingSlot()
        paymentSourceSlot = CapturingSlot()
        paginatedObjectSlot = CapturingSlot()
    }

    @Test
    fun `add Payment Source returns Data`() {
        //Given
        every {
            paymentRepository.addPaymentSource(paymentSourceWrapper, capture(paymentSourceSlot))
        } answers {
            paymentSourceSlot.captured.invoke(null, paymentSource.asModel)
        }
        //When
        paymentFeature.addPaymentSource(paymentSourceWrapper) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(paymentSource.asModel, response)
        }
    }

    @Test
    fun `add Payment Source returns Exception`() {
        //Given
        every {
            paymentRepository.addPaymentSource(paymentSourceWrapper, capture(paymentSourceSlot))
        } answers {
            paymentSourceSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentFeature.addPaymentSource(paymentSourceWrapper) { error, response ->
            assert(error is Exception)
            assertEquals(ERROR_MESSAGE, error?.message)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get My Payment Sources returns Data`() {
        //Given
        val paymentSourceWrappers = paymentSources.map { it.asModel }
        val paginatedObject = PaginatedObject<PaymentSource?>(paymentSourceWrappers, NEXT_PAGE)
        every {
            paymentRepository.getMyPaymentSources(PAGE_SIZE, PAGE, capture(paginatedObjectSlot))
        } answers {
            paginatedObjectSlot.captured.invoke(null, paginatedObject)
        }
        //When
        paymentFeature.getMyPaymentSources(PAGE_SIZE, PAGE) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(paginatedObject, response)
        }
    }

    @Test
    fun `get My Payment Sources returns Exception`() {
        //Given
        every {
            paymentRepository.getMyPaymentSources(PAGE_SIZE, PAGE, capture(paginatedObjectSlot))
        } answers {
            paginatedObjectSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentFeature.getMyPaymentSources(PAGE_SIZE, PAGE) { error, response ->
            assert(error is Exception)
            assertEquals(ERROR_MESSAGE, error?.message)
            assertEquals(null, response)
        }
    }

    @Test
    fun `create Payment Intent returns Data`() {
        //Given
        every {
            paymentFeature.createPaymentIntent(paymentIntentWrapper, capture(paymentIntentSlot))
        } answers {
            paymentIntentSlot.captured.invoke(null, paymentIntent)
        }
        //When
        paymentFeature.createPaymentIntent(paymentIntentWrapper) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(paymentIntent, response)
        }
    }

    @Test
    fun `create Payment Intent returns Exception`() {
        //Given
        every {
            paymentFeature.createPaymentIntent(paymentIntentWrapper, capture(paymentIntentSlot))
        } answers {
            paymentIntentSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentFeature.createPaymentIntent(paymentIntentWrapper) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(ERROR_MESSAGE, error?.message)
            assertEquals(null, response)
        }
    }

    @Test
    fun `update Payment Intent returns Data`() {
        //Given
        every {
            paymentRepository.updatePaymentIntent(
                PAYMENT_INTENT_ID,
                paymentIntentUpdateWrapper,
                capture(paymentIntentSlot)
            )
        } answers {
            paymentIntentSlot.captured.invoke(null, paymentIntent)
        }
        //When
        paymentFeature.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateWrapper
        ) { error, response ->
            assertEquals(null, error)
            assertEquals(paymentIntent, response)
        }
    }

    @Test
    fun `update Payment Intent returns Error`() {
        //Given
        every {
            paymentRepository.updatePaymentIntent(
                PAYMENT_INTENT_ID,
                paymentIntentUpdateWrapper,
                capture(paymentIntentSlot)
            )
        } answers {
            paymentIntentSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentFeature.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateWrapper
        ) { error, response ->
            assert(error is Exception)
            assertEquals(ERROR_MESSAGE, error?.message)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get My Payment Intent returns Data`() {
        //Given
        every {
            paymentRepository.getMyPaymentIntent(PAYMENT_INTENT_ID, capture(paymentIntentSlot))
        } answers {
            paymentIntentSlot.captured.invoke(null, paymentIntent)
        }
        //When
        paymentFeature.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            assertEquals(null, error)
            assertEquals(paymentIntent, response)
        }
    }

    @Test
    fun `get My Payment Intent returns Error`() {
        //Given
        every {
            paymentRepository.getMyPaymentIntent(PAYMENT_INTENT_ID, capture(paymentIntentSlot))
        } answers {
            paymentIntentSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentFeature.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            assert(error is Exception)
            assertEquals(ERROR_MESSAGE, error?.message)
            assertEquals(null, response)
        }
    }

    @Test
    fun `delete My Payment Source returns Data`() {
        //Given
        every {
            paymentRepository.deleteMyPaymentSource(PAYMENT_INTENT_ID, capture(paymentSourceSlot))
        } answers {
            paymentSourceSlot.captured.invoke(null, paymentSource.asModel)
        }
        //When
        paymentFeature.deletePaymentSource(PAYMENT_INTENT_ID) { error, response ->
            assertEquals(null, error)
            assertEquals(paymentSource.asModel, response)
        }
    }

    @Test
    fun `delete My Payment Source returns Error`() {
        //Given
        every {
            paymentRepository.deleteMyPaymentSource(PAYMENT_INTENT_ID, capture(paymentSourceSlot))
        } answers {
            paymentSourceSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentFeature.deletePaymentSource(PAYMENT_INTENT_ID) { error, response ->
            assert(error is Exception)
            assertEquals(ERROR_MESSAGE, error?.message)
            assertEquals(null, response)
        }
    }


    companion object {
        private const val ERROR_MESSAGE = "Time to Crash baby!"
        private const val PAYMENT_INTENT_ID = "2"
        private const val PAGE_SIZE = 10
        private const val PAGE = 2
        private const val NEXT_PAGE = 3

    }

}