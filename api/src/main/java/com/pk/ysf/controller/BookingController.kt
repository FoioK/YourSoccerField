package com.pk.ysf.controller

import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.dto.BookingInput
import com.pk.ysf.service.BookingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("\${spring.data.rest.base-path}/bookings")
class BookingController @Autowired constructor(
        private var bookingService: BookingService
) {

    @PostMapping(
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
    )
    fun create(@Valid @RequestBody bookingInput: BookingInput): ResponseEntity<BookingDetails> {
        val bookingDetails: BookingDetails = this.bookingService.create(bookingInput)

        return ResponseEntity
                .created(URI.create("/bookings/${bookingDetails.id}"))
                .body(bookingDetails)
    }

}