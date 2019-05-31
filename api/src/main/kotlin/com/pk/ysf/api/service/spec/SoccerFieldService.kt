package com.pk.ysf.api.service.spec

import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.SoccerFieldDetails
import com.pk.ysf.api.model.dto.SoccerFieldInput
import org.springframework.security.access.prepost.PreAuthorize

interface SoccerFieldService {

    @PreAuthorize("hasAuthority(T(com.pk.ysf.api.security.Permissions).SOCCERFIELDS_GET_ALL)")
    fun findAll(): List<SoccerFieldDetails>

    @PreAuthorize("hasAuthority(T(com.pk.ysf.api.security.Permissions).SOCCERFIELDS_GET_BY_ID)")
    fun findById(soccerFieldId: Long): SoccerFieldDetails

    fun findExampleTen(): List<SoccerFieldDetails>

    fun findByAddressContaining(value: String): List<SoccerFieldDetails>

    fun findByCustomCriteria(encodedObject: String): List<SoccerFieldDetails>

    fun findAllBookings(soccerFieldId: Long): List<BookingDetails>

    @PreAuthorize("hasAuthority(T(com.pk.ysf.api.security.Permissions).SOCCERFIELDS_POST_CREATE)")
    fun create(requestBody: SoccerFieldInput): SoccerFieldDetails

}