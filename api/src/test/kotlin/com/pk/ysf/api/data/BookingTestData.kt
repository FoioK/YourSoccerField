package com.pk.ysf.api.data

import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.entity.Booking

fun bookingInputMock(
        startDate: String = START_DATE_STRING
): BookingInput =
        BookingInput.build {
            this.userCode = USER_CODE
            this.startDate = startDate
            this.executionTime = EXECUTION_TIME_STRING
            this.amount = AMOUNT_STRING
            this.isPayed = IS_PAYED
            this.soccerField = SOCCER_FIELD_ID
        }

fun bookingDetailsMock(): BookingDetails =
        BookingDetails.build {
            id = BOOKING_ID
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