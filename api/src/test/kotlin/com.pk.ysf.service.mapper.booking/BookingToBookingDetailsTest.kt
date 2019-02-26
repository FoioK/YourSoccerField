package com.pk.ysf.service.mapper.booking

import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.entity.Booking
import com.pk.ysf.util.DateUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RunWith(SpringRunner::class)
class BookingToBookingDetailsTest {

    @InjectMocks
    lateinit var bookingToBookingDetails: BookingToBookingDetails

    companion object {
        const val ID = 3L
        const val USER_CODE = 5L
        val START_DATE: LocalDateTime = LocalDateTime.parse(
                "2019-05-05 11:00",
                DateTimeFormatter.ofPattern(DateUtil.shortPattern)
        )
        val EXECUTION_TIME: LocalTime = LocalTime.parse("02:30")
        val AMOUNT: BigDecimal = BigDecimal.valueOf(150.00)
        const val IS_PAYED = false
        //TODO jak powstanie builder w modelu soccer field -> get soccer field mock
    }

    @Test
    fun correctMapBooking() {
        val bookingDetails: BookingDetails = bookingToBookingDetails.map(correctBooking())
    }

    private fun correctBooking(): Booking {
        return Booking.build {
            id = ID
            userCode = USER_CODE
            startDate = START_DATE
            executionTime = EXECUTION_TIME
            amount = AMOUNT
            isPayed = IS_PAYED
            //TODO dodanie soccer field
        }
    }
}