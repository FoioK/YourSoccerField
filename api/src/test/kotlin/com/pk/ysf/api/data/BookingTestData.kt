package com.pk.ysf.api.data

import com.pk.ysf.apimodels.dto.BookingInput
import com.pk.ysf.apimodels.entity.Booking

fun correctBookingInput(): BookingInput =
        BookingInput.build {
            userCode = USER_CODE
            startDate = START_DATE_STRING
            executionTime = EXECUTION_TIME_STRING
            amount = AMOUNT_STRING
            isPayed = IS_PAYED
            soccerField = SOCCER_FIELD_ID
        }

fun bookingMock(): Booking =
        Booking.build {
            id = BOOKING_ID
            userCode = USER_CODE
            startDate = START_DATE
            executionTime = EXECUTION_TIME
            amount = AMOUNT
            isPayed = IS_PAYED
            soccerField = soccerFieldMock()
        }