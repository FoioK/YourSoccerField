package com.pk.ysf.controller;

import com.pk.ysf.service.BookingService;
import com.pk.ysf.service.dtoModel.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${spring.data.rest.base-path}/bookings")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).BOOKINGS_POST_CREATE)")
    public ResponseEntity<?> create(
            @Valid @RequestBody BookingDTO bookingDTO
    ) {
        return new ResponseEntity<>(
                this.bookingService.create(bookingDTO),
                HttpStatus.CREATED
        );
    }
}
