package com.pk.ysf.api.service.spec

import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.BookingInput
import org.springframework.security.access.prepost.PreAuthorize

interface BookingService {

    @PreAuthorize("hasAuthority(T(com.pk.ysf.api.security.Permissions).BOOKINGS_POST_CREATE)")
    fun create(bookingInput: BookingInput): BookingDetails

}