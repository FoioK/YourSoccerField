package com.pk.ysf.controller;

import com.pk.ysf.service.UserService;
import com.pk.ysf.service.dtoModel.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("${spring.data.rest.base-path}/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(
                userService.createUser(userDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping(
            value = "/{userId}/bookings",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAllBookings(@PathVariable Long userId) {
        return new ResponseEntity<>(
                this.userService.getAllBookingsByUserId(userId),
                HttpStatus.OK
        );
    }

    @PostMapping(
            value = "/admin/authenticate",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, Boolean>> adminAuthenticate(
            @RequestParam Map<String, String> params) {

        return new ResponseEntity<>(
                Collections.singletonMap("success", this.userService.adminAuthenticate(params)),
                HttpStatus.ACCEPTED
        );
    }
}
