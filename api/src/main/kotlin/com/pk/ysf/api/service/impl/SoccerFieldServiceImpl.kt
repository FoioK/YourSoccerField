package com.pk.ysf.api.service.impl

import com.pk.ysf.api.model.dto.SoccerFieldDetails
import com.pk.ysf.api.model.exception.MissingEntityException
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.service.mapper.soccerField.SoccerFieldToSoccerFieldDetails
import com.pk.ysf.api.service.spec.SoccerFieldService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SoccerFieldServiceImpl @Autowired constructor(
        private val soccerFieldRepository: SoccerFieldRepository,
        private val soccerFieldToSoccerFieldDetails: SoccerFieldToSoccerFieldDetails
) : SoccerFieldService {

    override fun findAll(): List<SoccerFieldDetails> =
            soccerFieldToSoccerFieldDetails
                    .mapAll(soccerFieldRepository.findAll())
                    .toList()

    override fun findById(soccerFieldId: Long): SoccerFieldDetails =
            soccerFieldToSoccerFieldDetails
                    .map(soccerFieldRepository
                            .findById(soccerFieldId)
                            .orElseThrow {
                                MissingEntityException("Cannot find soccer field with id $soccerFieldId")
                            }
                    )

    override fun findExampleTen(): List<SoccerFieldDetails> =
            soccerFieldToSoccerFieldDetails
                    .mapAll(soccerFieldRepository.findExampleTen())
                    .toList()

    override fun findByStreetContains(value: String): List<SoccerFieldDetails> =
            soccerFieldToSoccerFieldDetails
                    .mapAll(soccerFieldRepository.findByAddressContaining(value))
                    .toList()
}