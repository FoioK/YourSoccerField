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
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("${spring.data.rest.base-path}/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(
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

    @PutMapping(
            value = "/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).USERS_PUT_UPDATE)")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long userId,
            @RequestBody UserDTO userDTO
    ) {
        Optional<UserDTO> result =
                this.userService.updateUser(
                        userId,
                        userDTO
                );

        if (result.isPresent()) {
            return ResponseEntity
                    .created(URI.create(String.format("/users/%d", userId)))
                    .build();
        }
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(
            value = "/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).USERS_DELETE_BY_ID)")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId
    ) {
        this.userService.deleteUser(userId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
