package com.pk.ysf.api.service.mapper.booking

import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.util.SHORT_DATE_PATTERN
import com.pk.ysf.api.model.dto.BookingDetails
import com.pk.ysf.api.model.entity.Booking
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class BookingToBookingDetails : BaseMapper<Booking, BookingDetails> {

    override fun map(from: Booking): BookingDetails =
            BookingDetails(
                    from.id,
                    from.userCode,
                    from.startDate.format(DateTimeFormatter.ofPattern(SHORT_DATE_PATTERN)),
                    from.executionTime.toString(),
                    from.amount.toString(),
                    from.isPayed,
                    from.soccerField.id
            )

}