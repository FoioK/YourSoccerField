package com.pk.ysf.api.controller

import com.pk.ysf.api.model.dto.SurfaceDetails
import com.pk.ysf.api.service.spec.SurfaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${spring.data.rest.base-path}/bookings")
class SurfacesController @Autowired constructor(
        private val surfaceService: SurfaceService
) {

    @GetMapping(
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
    )
    fun getAll(): ResponseEntity<List<SurfaceDetails>> = ResponseEntity.ok(surfaceService.getAll())

}