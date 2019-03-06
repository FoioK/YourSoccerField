package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.apimodels.entity.Booking
import com.pk.ysf.api.util.DateUtil
import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.entity.SoccerField
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
class BookingToBookingDetailsTest {

    @InjectMocks
    lateinit var bookingToBookingDetails: BookingToBookingDetails

    companion object {
        const val ID = 3L
        const val USER_CODE = 5L
        const val START_DATE_STRING = "2019-05-05 11:00"
        val START_DATE: LocalDateTime = LocalDateTime.parse(
                START_DATE_STRING,
                DateTimeFormatter.ofPattern(DateUtil.shortPattern)
        )
        const val EXECUTION_TIME_STRING = "02:30"
        val EXECUTION_TIME: LocalTime = LocalTime.parse(EXECUTION_TIME_STRING)
        const val AMOUNT_STRING = "150.00"
        val AMOUNT: BigDecimal = BigDecimal(AMOUNT_STRING)
        const val IS_PAYED = false

        private const val SOCCER_FIELD_ID = 5L
        private const val SOCCER_FIELD_NAME = "SoccerField"
        private const val SOCCER_FIELD_WIDTH = 40
        private const val SOCCER_FIELD_LENGTH = 60
        private const val PRICE = "120.00"
        private const val IS_LIGHTING = true
        private const val IS_FENCED = false
        private const val IS_LOCKER_ROOM = true
        private const val DESCRIPTION = "Soccer field description"
        private val SOCCER_FIELD_PRICE: BigDecimal = BigDecimal(PRICE)
        val SOCCER_FIELD: SoccerField = SoccerField.build {
            id = SOCCER_FIELD_ID
            name = SOCCER_FIELD_NAME
            width = SOCCER_FIELD_WIDTH
            length = SOCCER_FIELD_LENGTH
            price = SOCCER_FIELD_PRICE
            isLighting = IS_LIGHTING
            isFenced = IS_FENCED
            isLockerRoom = IS_LOCKER_ROOM
            description = DESCRIPTION
        }
    }

    @Test
    fun correctMapBooking() {
        val bookingDetails: BookingDetails = bookingToBookingDetails.map(correctBooking())

        assertEquals(ID, bookingDetails.id)
        assertEquals(USER_CODE, bookingDetails.userCode)
        assertEquals(START_DATE_STRING, bookingDetails.startDate)
        assertEquals(EXECUTION_TIME_STRING, bookingDetails.executionTime)
        assertEquals(AMOUNT_STRING, bookingDetails.amount)
        assertEquals(SOCCER_FIELD_ID, bookingDetails.soccerField)
    }

    @Test
    fun correctMapAll() {
        val bookingDetailsList: Collection<BookingDetails> = bookingToBookingDetails.mapAll(
                listOf(correctBooking(), correctBooking(), correctBooking())
        )

        bookingDetailsList.forEach {
            assertEquals(ID, it.id)
            assertEquals(USER_CODE, it.userCode)
            assertEquals(START_DATE_STRING, it.startDate)
            assertEquals(EXECUTION_TIME_STRING, it.executionTime)
            assertEquals(AMOUNT_STRING, it.amount)
            assertEquals(SOCCER_FIELD_ID, it.soccerField)
        }
    }

    private fun correctBooking(): Booking {
        return Booking.build {
            id = ID
            userCode = USER_CODE
            startDate = START_DATE
            executionTime = EXECUTION_TIME
            amount = AMOUNT
            isPayed = IS_PAYED
            soccerField = SOCCER_FIELD
        }
    }
}