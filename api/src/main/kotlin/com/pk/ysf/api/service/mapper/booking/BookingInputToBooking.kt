package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.api.repository.SoccerFieldRepository
import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.util.SHORT_DATE_PATTERN
import com.pk.ysf.api.model.dto.BookingInput
import com.pk.ysf.api.model.entity.Booking
import com.pk.ysf.api.model.entity.SoccerField
import com.pk.ysf.api.model.exception.MissingEntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component
class BookingInputToBooking @Autowired constructor(
        private val soccerFieldRepository: SoccerFieldRepository
) : BaseMapper<BookingInput, Booking> {

    override fun map(from: BookingInput): Booking =
            Booking(
                    0,
                    from.userCode,
                    LocalDateTime.parse(
                            from.startDate,
                            DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)
                    ),
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