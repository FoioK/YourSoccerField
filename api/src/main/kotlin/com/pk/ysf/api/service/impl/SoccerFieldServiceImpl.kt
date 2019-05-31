package com.pk.ysf.api.service.impl

import com.pk.ysf.api.annotation.ValidInput
import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.dto.SearchModel
import com.pk.ysf.api.model.dto.SoccerFieldDetails
import com.pk.ysf.api.model.dto.SoccerFieldInput
import com.pk.ysf.api.model.entity.SoccerField
import com.pk.ysf.api.model.exception.MissingEntityException
import com.pk.ysf.api.repository.BookingRepository
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.service.mapper.booking.BookingToBookingDetails
import com.pk.ysf.api.service.mapper.soccerField.SoccerFieldInputToSoccerField
import com.pk.ysf.api.service.mapper.soccerField.SoccerFieldToSoccerFieldDetails
import com.pk.ysf.api.service.spec.SoccerFieldService
import com.pk.ysf.api.service.util.SearchFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SoccerFieldServiceImpl @Autowired constructor(
        private val soccerFieldRepository: SoccerFieldRepository,
        private val bookingRepository: BookingRepository,
        private val searchFactory: SearchFactory,

        private val soccerFieldToSoccerFieldDetails: SoccerFieldToSoccerFieldDetails,
        private val soccerFieldInputToSoccerField: SoccerFieldInputToSoccerField,
        private val bookingToBookingDetails: BookingToBookingDetails
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

    override fun findByAddressContaining(value: String): List<SoccerFieldDetails> =
            soccerFieldToSoccerFieldDetails
                    .mapAll(soccerFieldRepository.findByAddressContaining(value))
                    .toList()

    override fun findByCustomCriteria(encodedObject: String): List<SoccerFieldDetails> {
        val searchModel: SearchModel = this.searchFactory.parseToModel(encodedObject)
        val soccerFields = this.searchFactory
                .getSoccerFieldsByCustomCriteria(searchModel)

        return soccerFieldToSoccerFieldDetails
                .mapAll(soccerFields)
                .toList()
    }

    override fun findAllBookings(soccerFieldId: Long): List<BookingDetails> =
            bookingToBookingDetails
                    .mapAll(bookingRepository.findAllBySoccerField(soccerFieldId))
                    .toList()

    @ValidInput
    override fun create(requestBody: SoccerFieldInput): SoccerFieldDetails {
        val soccerField: SoccerField = this.soccerFieldInputToSoccerField.map(requestBody)
        val createdResource: SoccerField = this.soccerFieldRepository.save(soccerField)

        return this.soccerFieldToSoccerFieldDetails.map(createdResource)
    }

}