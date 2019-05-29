package com.pk.ysf.api.service.spec

import com.pk.ysf.api.model.dto.SoccerFieldDetails
import org.springframework.security.access.prepost.PreAuthorize

interface SoccerFieldService {

    @PreAuthorize("hasAuthority(T(com.pk.ysf.api.security.Permissions).SOCCERFIELDS_GET_ALL)")
    fun findAll(): List<SoccerFieldDetails>

    @PreAuthorize("hasAuthority(T(com.pk.ysf.api.security.Permissions).SOCCERFIELDS_GET_BY_ID)")
    fun findById(soccerFieldId: Long): SoccerFieldDetails

    fun findExampleTen(): List<SoccerFieldDetails>

    fun findByStreetContains(value: String): List<SoccerFieldDetails>

}