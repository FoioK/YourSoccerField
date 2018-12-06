package com.pk.YourSoccerField.controller;

import com.pk.YourSoccerField.service.SoccerFieldService;
import com.pk.YourSoccerField.service.dtoModel.SoccerFieldDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class SoccerFieldController {

    private SoccerFieldService soccerFieldService;

    @Autowired
    public SoccerFieldController(SoccerFieldService soccerFieldService) {
        this.soccerFieldService = soccerFieldService;
    }

    @GetMapping(
            value = "/soccerfields",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SOCCERFIELDS_GET_ALL')")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(
                this.soccerFieldService.getAll(),
                HttpStatus.ACCEPTED
        );
    }

    @PostMapping(
            value = "/soccerfields",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SOCCERFIELDS_POST_CREATE')")
    public ResponseEntity<?> createSoccerField(
            @Valid @RequestBody SoccerFieldDTO soccerFieldDTO) {
        return new ResponseEntity<>(
                this.soccerFieldService.createSoccerField(soccerFieldDTO),
                HttpStatus.CREATED
        );
    }
}
