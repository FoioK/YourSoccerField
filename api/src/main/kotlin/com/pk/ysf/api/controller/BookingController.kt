package com.pk.ysf.api.controller

import com.pk.ysf.api.service.spec.BookingService
import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.BookingInput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("\${spring.data.rest.base-path}/bookings")
class BookingController @Autowired constructor(
        private var bookingService: BookingService
) {

    @PostMapping(
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
    )
    fun create(@RequestBody bookingInput: BookingInput): ResponseEntity<BookingDetails> {
        val bookingDetails: BookingDetails = this.bookingService.create(bookingInput)
        // TODO poprawić odpowiedź, dodać HttpServletRequest do parametrów i zwrócić dokładne URI
        return ResponseEntity
                .created(URI.create("/bookings/${bookingDetails.id}"))
                .body(bookingDetails)
    }

}