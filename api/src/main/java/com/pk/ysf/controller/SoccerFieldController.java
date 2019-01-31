package com.pk.ysf.controller;

import com.pk.ysf.service.SoccerFieldService;
import com.pk.ysf.service.dtoModel.SoccerFieldDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("${spring.data.rest.base-path}/soccerfields")
public class SoccerFieldController {

    private SoccerFieldService soccerFieldService;

    @Autowired
    public SoccerFieldController(SoccerFieldService soccerFieldService) {
        this.soccerFieldService = soccerFieldService;
    }

    @ApiOperation(
            value = "get all soccer field",
            authorizations = {
                    @Authorization(
                            value = "ysf_oauth",
                            scopes = @AuthorizationScope(
                                    scope = "SOCCERFIELDS_GET_ALL",
                                    description = "allows get all soccer field"
                            )
                    )
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Dostęp zabroniony"),
    })
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).SOCCERFIELDS_GET_ALL)")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(
                this.soccerFieldService.getAll(),
                HttpStatus.ACCEPTED
        );
    }

    @ApiOperation(
            value = "create soccer field",
            authorizations = {
                    @Authorization(
                            value = "ysf_oauth",
                            scopes = @AuthorizationScope(
                                    scope = "SOCCERFIELDS_POST_CREATE",
                                    description = "allows create soccer field"
                            )
                    )
            }
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 401, message = "Dostęp zabroniony"),
            @ApiResponse(code = 404, message = "Missing entity exception")
    })
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).SOCCERFIELDS_POST_CREATE)")
    public ResponseEntity<?> createSoccerField(
            @Valid @RequestBody SoccerFieldDTO soccerFieldDTO
    ) {
        return new ResponseEntity<>(
                this.soccerFieldService.createSoccerField(soccerFieldDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping(
            value = "/{soccerFieldId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority(T(com.pk.ysf.util.Permissions).SOCCERFIELDS_GET_BY_ID)")
    public ResponseEntity<?> getById(@PathVariable Long soccerFieldId) {
        return new ResponseEntity<>(
                this.soccerFieldService.getById(soccerFieldId),
                HttpStatus.OK
        );
    }

    @GetMapping(
            value = "/byStreet/{street}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByAddressContains(
            @PathVariable @NotBlank String street
    ) {
        return new ResponseEntity<>(
                this.soccerFieldService.getByAddressContains(street),
                HttpStatus.OK
        );
    }

    @GetMapping(
            value = "/exampleTen",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getExampleTen() {
        return new ResponseEntity<>(
                this.soccerFieldService.getExampleTen(),
                HttpStatus.OK
        );
    }

    @GetMapping(
            value = "/advancedSearch",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByCustomCriteria(
            @RequestParam String encodedObject
    ) {
        return new ResponseEntity<>(
                this.soccerFieldService.getByCustomCriteria(encodedObject),
                HttpStatus.OK
        );
    }

    @GetMapping(
            value = "/{soccerFieldId}/bookings",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAllBookings(
            @NotNull @Positive @PathVariable Long soccerFieldId
    ) {
        return new ResponseEntity<>(
                this.soccerFieldService.getAllBookings(soccerFieldId),
                HttpStatus.OK
        );
    }
}
