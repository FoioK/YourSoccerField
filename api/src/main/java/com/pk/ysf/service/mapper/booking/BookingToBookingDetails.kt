package com.pk.ysf.service.mapper.booking

import com.pk.ysf.apimodels.dto.BookingDetails
import com.pk.ysf.apimodels.entity.Booking
import com.pk.ysf.service.mapper.BaseMapper
import org.springframework.stereotype.Component

@Component
class BookingToBookingDetails : BaseMapper<Booking, BookingDetails> {

    override fun map(from: Booking): BookingDetails {
        return BookingDetails(
                from.id,
                from.userCode,
                from.startDate.toString(),
                from.executionTime.toString(),
                from.amount.toString(),
                from.isPayed,
                from.soccerField?.id
        )
    }

}