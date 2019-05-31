package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.entity.Booking
import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.util.dateTimeToString
import org.springframework.stereotype.Component

@Component
class BookingToBookingDetails : BaseMapper<Booking, BookingDetails> {

    override fun map(from: Booking): BookingDetails =
            BookingDetails(
                    from.id,
                    from.userCode,
                    dateTimeToString(from.startDate),
                    from.executionTime.toString(),
                    from.amount.toString(),
                    from.isPayed,
                    from.soccerField.id
            )

}