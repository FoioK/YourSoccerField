package com.pk.ysf.controller;

import com.pk.ysf.service.SoccerFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${spring.data.rest.base-path}/surfaces")
public class SurfaceController {

    private final SoccerFieldService soccerFieldService;

    @Autowired
    public SurfaceController(SoccerFieldService soccerFieldService) {
        this.soccerFieldService = soccerFieldService;
    }

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAllSurface() {
        return new ResponseEntity<>(
                this.soccerFieldService.getAllSurface(),
                HttpStatus.OK
        );
    }
}
