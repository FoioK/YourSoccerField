package com.pk.ysf.api.controller

import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.SoccerFieldDetails
import com.pk.ysf.api.model.dto.SoccerFieldInput
import com.pk.ysf.api.service.spec.SoccerFieldService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("\${spring.data.rest.base-path}/bookings")
class SoccerFieldController @Autowired constructor(
        private val soccerFieldService: SoccerFieldService
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): ResponseEntity<List<SoccerFieldDetails>> =
            ResponseEntity.ok().body(soccerFieldService.findAll())

    @GetMapping("/{soccerFieldId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(soccerFieldId: Long): ResponseEntity<SoccerFieldDetails> =
            ResponseEntity.ok().body(soccerFieldService.findById(soccerFieldId))

    @GetMapping(
            "/exampleTen",
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun findExampleTen(): ResponseEntity<List<SoccerFieldDetails>> =
            ResponseEntity.ok().body(soccerFieldService.findExampleTen())

    @GetMapping(
            "/byStreet/{street}",
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun findByAddressContaining(value: String): ResponseEntity<List<SoccerFieldDetails>> =
            ResponseEntity.ok().body(soccerFieldService.findByAddressContaining(value))

    @GetMapping("/advancedSearch",
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun findByCustomCriteria(encodedObject: String): ResponseEntity<List<SoccerFieldDetails>> =
            ResponseEntity.ok().body(soccerFieldService.findByCustomCriteria(encodedObject))

    @GetMapping("/{soccerFieldId}/bookings", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllBookings(soccerFieldId: Long): ResponseEntity<List<BookingDetails>> =
            ResponseEntity.ok().body(soccerFieldService.findAllBookings(soccerFieldId))

    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(
            @RequestBody requestBody: SoccerFieldInput,
            request: HttpServletRequest
    ): ResponseEntity<SoccerFieldDetails> {
        val createdSoccerField: SoccerFieldDetails = soccerFieldService.create(requestBody)

        return ResponseEntity.created(URI.create(request.requestURI + "/${createdSoccerField.id}"))
                .body(createdSoccerField)
    }

}