package com.pk.ysf.service.spec

import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.dto.BookingInput

interface BookingService {

    fun create(bookingInput: BookingInput): BookingDetails

}