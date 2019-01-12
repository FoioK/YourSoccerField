package com.pk.ysf.controller;

import com.pk.ysf.service.UserService;
import com.pk.ysf.service.dtoModel.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(
            value = "/admin/authenticate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).USERS_ADMIN_PANE)")
    public ResponseEntity<Map<String, Boolean>> adminPaneAuthenticate() {
        return new ResponseEntity<>(
                Collections.singletonMap("success", true),
                HttpStatus.ACCEPTED
        );
    }
}
