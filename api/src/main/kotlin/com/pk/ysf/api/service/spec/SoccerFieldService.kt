package com.pk.ysf.api.service.spec

import com.pk.ysf.api.model.dto.SoccerFieldDetails

interface SoccerFieldService {

    fun findAll(): List<SoccerFieldDetails>

    fun findById(soccerFieldId: Long): SoccerFieldDetails

}