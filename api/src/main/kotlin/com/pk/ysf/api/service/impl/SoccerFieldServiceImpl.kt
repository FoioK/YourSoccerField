package com.pk.ysf.api.service.impl

import com.pk.ysf.api.model.dto.SoccerFieldDetails
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.service.spec.SoccerFieldService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SoccerFieldServiceImpl @Autowired constructor(
        private val soccerFieldRepository: SoccerFieldRepository
): SoccerFieldService {

    override fun findAll(): List<SoccerFieldDetails> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(soccerFieldId: Long): SoccerFieldDetails {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}