package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.api.data.*
import com.pk.ysf.apimodels.dto.BookingDetails
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
class BookingToBookingDetailsTest {

    @InjectMocks
    lateinit var bookingToBookingDetails: BookingToBookingDetails

    @Test
    fun correctMapBooking() {
        val bookingDetails: BookingDetails = bookingToBookingDetails.map(bookingMock())

        assertEquals(BOOKING_ID, bookingDetails.id)
        assertEquals(USER_CODE, bookingDetails.userCode)
        assertEquals(START_DATE_STRING, bookingDetails.startDate)
        assertEquals(EXECUTION_TIME_STRING, bookingDetails.executionTime)
        assertEquals(AMOUNT_STRING, bookingDetails.amount)
        assertEquals(SOCCER_FIELD_ID, bookingDetails.soccerField)
    }

    @Test
    fun correctMapAll() {
        val bookingDetailsList: Collection<BookingDetails> = bookingToBookingDetails.mapAll(
                listOf(bookingMock(), bookingMock(), bookingMock())
        )

        bookingDetailsList.forEach {
            assertEquals(BOOKING_ID, it.id)
            assertEquals(USER_CODE, it.userCode)
            assertEquals(START_DATE_STRING, it.startDate)
            assertEquals(EXECUTION_TIME_STRING, it.executionTime)
            assertEquals(AMOUNT_STRING, it.amount)
            assertEquals(SOCCER_FIELD_ID, it.soccerField)
        }
    }
}