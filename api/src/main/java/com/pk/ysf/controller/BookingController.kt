package com.pk.ysf.controller

import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.dto.BookingInput
import com.pk.ysf.service.BookingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDateTime

@RestController
@RequestMapping("\${spring.data.rest.base-path}/bookings")
class BookingController @Autowired constructor(
        private var bookingService: BookingService
) {

//    @PostMapping(
//            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
//            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
//    )
//    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).BOOKINGS_POST_CREATE)")
//    fun create(@RequestBody bookingInput: BookingInput): ResponseEntity<BookingDetails> {
//        var bookingDetails: BookingDetails = this.bookingService.create(bookingInput)
//
//        return ResponseEntity
//                .created(URI.create("/bookings/${bookingDetails.id}"))
//                .body(bookingDetails)
//    }

}