package com.pk.ysf.service.mapper.booking

import com.pk.ysf.apimodels.dto.BookingInput
import com.pk.ysf.apimodels.entity.Booking
import com.pk.ysf.apimodels.entity.SoccerField
import com.pk.ysf.apimodels.exception.ErrorCode
import com.pk.ysf.apimodels.exception.MissingEntityException
import com.pk.ysf.repository.SoccerFieldRepository
import com.pk.ysf.service.mapper.BaseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class BookingInputToBooking @Autowired constructor(
        private val soccerFieldRepository: SoccerFieldRepository
) : BaseMapper<BookingInput, Booking> {

    override fun map(from: BookingInput): Booking {
        return Booking(
                0,
                from.userCode,
                LocalDateTime.parse(from.startDate),
                LocalTime.parse(from.executionTime),
                BigDecimal(from.amount),
                from.isPayed,
                this.mapSoccerField(from.soccerField)
        )
    }

    private fun mapSoccerField(soccerFieldId: Long): SoccerField {
        return this.soccerFieldRepository
                .findById(soccerFieldId)
                .orElseThrow {
                    MissingEntityException(
                            "Cannot find soccer field with id $soccerFieldId",
                            ErrorCode.NOT_FOUND_BY_ID
                    )
                }
    }
}