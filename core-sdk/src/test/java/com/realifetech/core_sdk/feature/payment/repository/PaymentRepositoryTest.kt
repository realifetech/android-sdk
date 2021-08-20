package com.realifetech.core_sdk.feature.payment.repository

import com.realifetech.core_sdk.data.payment.model.PaymentSource
import com.realifetech.core_sdk.data.payment.model.asModel
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.core_sdk.feature.payment.data.PaymentDataSource
import com.realifetech.core_sdk.feature.payment.mocks.PaymentIntentMocks.paymentIntent
import com.realifetech.core_sdk.feature.payment.mocks.PaymentIntentMocks.paymentIntentUpdateInput
import com.realifetech.core_sdk.feature.payment.mocks.PaymentIntentMocks.paymentIntentUpdateWrapper
import com.realifetech.core_sdk.feature.payment.mocks.PaymentIntentMocks.paymentIntentWrapper
import com.realifetech.core_sdk.feature.payment.mocks.PaymentSourcesMocks.paymentSource
import com.realifetech.core_sdk.feature.payment.mocks.PaymentSourcesMocks.paymentSourceInput
import com.realifetech.core_sdk.feature.payment.mocks.PaymentSourcesMocks.paymentSourceWrapper
import com.realifetech.core_sdk.feature.payment.mocks.PaymentSourcesMocks.paymentSources
import com.realifetech.fragment.PaymentIntent
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PaymentRepositoryTest {

    @RelaxedMockK
    private lateinit var paymentDataSource: PaymentDataSource
    private lateinit var paymentIntentSlot: CapturingSlot<(error: Exception?, response: PaymentIntent?) -> Unit>
    private lateinit var paymentSourceSlot: CapturingSlot<(error: Exception?, response: PaymentSource?) -> Unit>
    private lateinit var paginatedObjectSlot: CapturingSlot<(error: Exception?, response: PaginatedObject<PaymentSource?>?) -> Unit>
    private lateinit var paymentRepository: PaymentRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        paymentRepository = PaymentRepository(paymentDataSource)
        paymentIntentSlot = CapturingSlot()
        paymentSourceSlot = CapturingSlot()
        paginatedObjectSlot = CapturingSlot()
    }

    @Test
    fun `add Payment Source returns Data`() {
        //Given
        every {
            paymentDataSource.addPaymentSource(paymentSourceInput, capture(paymentSourceSlot))
        } answers {
            paymentSourceSlot.captured.invoke(null, paymentSource.asModel)
        }
        //When
        paymentRepository.addPaymentSource(paymentSourceWrapper) { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(paymentSource.asModel, response)
        }
    }

    @Test
    fun `add Payment Source returns Exception`() {
        //Given
        every {
            paymentDataSource.addPaymentSource(paymentSourceInput, capture(paymentSourceSlot))
        } answers {
            paymentSourceSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentRepository.addPaymentSource(paymentSourceWrapper) { error, response ->
            assert(error is Exception)
            Assert.assertEquals(ERROR_MESSAGE, error?.message)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get My Payment Sources returns Data`() {
        //Given
        val paymentSourceWrappers = paymentSources.map { it.asModel }
        val paginatedObject= PaginatedObject<PaymentSource?>(paymentSourceWrappers, NEXT_PAGE)
        every {
            paymentDataSource.getMyPaymentSources(PAGE_SIZE, PAGE, capture(paginatedObjectSlot))
        } answers {
            paginatedObjectSlot.captured.invoke(null, paginatedObject)
        }
        //When
        paymentRepository.getMyPaymentSources(PAGE_SIZE, PAGE) { error, response ->
        //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(paginatedObject, response)
        }
    }

    @Test
    fun `get My Payment Sources returns Exception`() {
        //Given
        every {
            paymentDataSource.getMyPaymentSources(PAGE_SIZE, PAGE, capture(paginatedObjectSlot))
        } answers {
            paginatedObjectSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentRepository.getMyPaymentSources(PAGE_SIZE, PAGE) { error, response ->
            assert(error is Exception)
            Assert.assertEquals(ERROR_MESSAGE, error?.message)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `create Payment Intent returns Data`() {
        //Given
        every {
            paymentRepository.createPaymentIntent(paymentIntentWrapper, capture(paymentIntentSlot))
        } answers {
            paymentIntentSlot.captured.invoke(null, paymentIntent)
        }
        //When
        paymentRepository.createPaymentIntent(paymentIntentWrapper) { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(paymentIntent, response)
        }
    }

    @Test
    fun `create Payment Intent returns Exception`() {
        //Given
        every {
            paymentRepository.createPaymentIntent(paymentIntentWrapper, capture(paymentIntentSlot))
        } answers {
            paymentIntentSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentRepository.createPaymentIntent(paymentIntentWrapper) { error, response ->
            //Then
            assert(error is Exception)
            Assert.assertEquals(ERROR_MESSAGE, error?.message)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `update Payment Intent returns Data`() {
        //Given
        every {
            paymentDataSource.updatePaymentIntent(
                PAYMENT_INTENT_ID,
                paymentIntentUpdateInput,
                capture(paymentIntentSlot)
            )
        } answers {
            paymentIntentSlot.captured.invoke(null, paymentIntent)
        }
        //When
        paymentRepository.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateWrapper
        ) { error, response ->
            Assert.assertEquals(null, error)
            Assert.assertEquals(paymentIntent, response)
        }
    }

    @Test
    fun `update Payment Intent returns Error`() {
        //Given
        every {
            paymentDataSource.updatePaymentIntent(
                PAYMENT_INTENT_ID,
                paymentIntentUpdateInput,
                capture(paymentIntentSlot)
            )
        } answers {
            paymentIntentSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentRepository.updatePaymentIntent(
            PAYMENT_INTENT_ID,
            paymentIntentUpdateWrapper
        ) { error, response ->
            assert(error is Exception)
            Assert.assertEquals(ERROR_MESSAGE, error?.message)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get My Payment Intent returns Data`() {
        //Given
        every {
            paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID, capture(paymentIntentSlot))
        } answers {
            paymentIntentSlot.captured.invoke(null, paymentIntent)
        }
        //When
        paymentRepository.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            Assert.assertEquals(null, error)
            Assert.assertEquals(paymentIntent, response)
        }
    }

    @Test
    fun `get My Payment Intent returns Error`() {
        //Given
        every {
            paymentDataSource.getMyPaymentIntent(PAYMENT_INTENT_ID, capture(paymentIntentSlot))
        } answers {
            paymentIntentSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentRepository.getMyPaymentIntent(PAYMENT_INTENT_ID) { error, response ->
            assert(error is Exception)
            Assert.assertEquals(ERROR_MESSAGE, error?.message)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `delete My Payment Source returns Data`() {
        //Given
        every {
            paymentDataSource.deleteMyPaymentSource(PAYMENT_INTENT_ID, capture(paymentSourceSlot))
        } answers {
            paymentSourceSlot.captured.invoke(null, paymentSource.asModel)
        }
        //When
        paymentRepository.deleteMyPaymentSource(PAYMENT_INTENT_ID) { error, response ->
            Assert.assertEquals(null, error)
            Assert.assertEquals(paymentSource.asModel, response)
        }
    }

    @Test
    fun `delete My Payment Source returns Error`() {
        //Given
        every {
            paymentDataSource.deleteMyPaymentSource(PAYMENT_INTENT_ID, capture(paymentSourceSlot))
        } answers {
            paymentSourceSlot.captured.invoke(Exception(ERROR_MESSAGE), null)
        }
        //When
        paymentRepository.deleteMyPaymentSource(PAYMENT_INTENT_ID) { error, response ->
            assert(error is Exception)
            Assert.assertEquals(ERROR_MESSAGE, error?.message)
            Assert.assertEquals(null, response)
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