package com.pk.ysf.api.controller

import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.service.spec.BookingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("\${spring.data.rest.base-path}/bookings")
class BookingController @Autowired constructor(
        private var bookingService: BookingService
) {

    @PostMapping(
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
    )
    fun create(
            @RequestBody bookingInput: BookingInput,
            request: HttpServletRequest): ResponseEntity<BookingDetails> {
        val bookingDetails: BookingDetails = this.bookingService.create(bookingInput)

        return ResponseEntity
                .created(URI.create(request.requestURI + "/${bookingDetails.id}"))
                .body(bookingDetails)
    }

}