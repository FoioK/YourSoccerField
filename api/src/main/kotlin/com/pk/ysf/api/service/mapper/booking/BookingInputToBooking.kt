package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.entity.Booking
import com.pk.ysf.api.model.entity.SoccerField
import com.pk.ysf.api.model.exception.MissingEntityException
import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.util.stringToDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalTime

@Component
class BookingInputToBooking @Autowired constructor(
        private val soccerFieldRepository: SoccerFieldRepository
) : BaseMapper<BookingInput, Booking> {

    override fun map(from: BookingInput): Booking =
            Booking(
                    0,
                    from.userCode,
                    stringToDateTime(from.startDate),
                    LocalTime.parse(from.executionTime),
                    BigDecimal(from.amount),
                    from.isPayed,
                    mapSoccerField(from.soccerField)
            )

    private fun mapSoccerField(soccerFieldId: Long): SoccerField =
            this.soccerFieldRepository
                    .findById(soccerFieldId)
                    .orElseThrow {
                        MissingEntityException("Cannot find soccer field with id $soccerFieldId")
                    }
}