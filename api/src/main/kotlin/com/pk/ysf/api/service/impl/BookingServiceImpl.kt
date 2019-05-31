package com.pk.ysf.api.service.impl

import com.pk.ysf.api.annotation.ValidInput
import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.entity.Booking
import com.pk.ysf.api.model.exception.ValidationException
import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.service.mapper.booking.BookingInputToBooking
import com.pk.ysf.api.service.mapper.booking.BookingToBookingDetails
import com.pk.ysf.api.service.spec.BookingService
import com.pk.ysf.api.validation.booking.BookingValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class BookingServiceImpl @Autowired constructor(
        private val bookingValidator: BookingValidator,
        private val bookingRepository: BookingRepository,
        private val bookingInputToBooking: BookingInputToBooking,
        private val bookingToBookingDetails: BookingToBookingDetails
) : BookingService {

    @ValidInput
    override fun create(@RequestBody bookingInput: BookingInput): BookingDetails {
        if (!bookingValidator.validate(bookingInput)) {
            throw ValidationException("Error occurred during data validation")
        }
        val booking: Booking = this.bookingInputToBooking.map(bookingInput)

        return this.bookingToBookingDetails.map(bookingRepository.save(booking))
    }

}