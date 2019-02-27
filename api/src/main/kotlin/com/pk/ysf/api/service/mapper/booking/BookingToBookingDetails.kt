package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.util.DateUtil
import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.entity.Booking
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class BookingToBookingDetails : BaseMapper<Booking, BookingDetails> {

    override fun map(from: Booking): BookingDetails {
        return BookingDetails(
                from.id,
                from.userCode,
                from.startDate.format(DateTimeFormatter.ofPattern(DateUtil.shortPattern)),
                from.executionTime.toString(),
                from.amount.toString(),
                from.isPayed,
                from.soccerField?.id
        )
    }

}