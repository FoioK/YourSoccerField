package com.pk.ysf.api.service.impl

import com.pk.ysf.api.data.any
import com.pk.ysf.api.data.bookingDetailsMock
import com.pk.ysf.api.data.bookingInputMock
import com.pk.ysf.api.data.bookingMock
import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.service.mapper.booking.BookingInputToBooking
import com.pk.ysf.api.service.mapper.booking.BookingToBookingDetails
import com.pk.ysf.api.validation.booking.BookingValidator
import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.entity.Booking
import de.jodamob.kotlin.testrunner.KotlinTestRunner
import org.junit.Before
import kotlin.test.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@RunWith(KotlinTestRunner::class)
class BookingServiceImplTest {

    @InjectMocks
    lateinit var bookingService: BookingServiceImpl

    @Mock
    lateinit var bookingValidator: BookingValidator

    @Mock
    lateinit var bookingRepository: BookingRepository

    @Mock
    lateinit var bookingInputToBooking: BookingInputToBooking

    @Mock
    lateinit var bookingToBookingDetails: BookingToBookingDetails

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(bookingValidator.validate(any(BookingInput::class.java)))
                .thenAnswer { true }
        Mockito.`when`(bookingInputToBooking.map(any(BookingInput::class.java)))
                .thenReturn(bookingMock())

        Mockito.`when`(bookingRepository.save(any(Booking::class.java)))
                .thenReturn(bookingMock())

        Mockito.`when`(bookingToBookingDetails.map(any(Booking::class.java)))
                .thenReturn(bookingDetailsMock())
    }

    @Test
    fun shouldCreateBooking() {
        val expectedBookingDetails: BookingDetails = bookingDetailsMock()
        val bookingDetails: BookingDetails = bookingService.create(bookingInputMock())

        assertEquals(expectedBookingDetails.id, bookingDetails.id)
        assertEquals(expectedBookingDetails.userCode, bookingDetails.userCode)
        assertEquals(expectedBookingDetails.startDate, bookingDetails.startDate)
        assertEquals(expectedBookingDetails.executionTime, bookingDetails.executionTime)
        assertEquals(expectedBookingDetails.amount, bookingDetails.amount)
        assertEquals(expectedBookingDetails.isPayed, bookingDetails.isPayed)
        assertEquals(expectedBookingDetails.soccerField, bookingDetails.soccerField)
    }
}