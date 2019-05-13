package com.pk.ysf.api.service.spec

import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.BookingInput

interface BookingService {

    fun create(bookingInput: BookingInput): BookingDetails

}