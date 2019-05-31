package com.pk.ysf.api.service.mapper.openHour

import com.pk.ysf.api.model.dto.OpenHourDetails
import com.pk.ysf.api.model.entity.OpenHour
import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.util.timeToString
import org.springframework.stereotype.Component

@Component
class OpenHourToOpenHourDetails : BaseMapper<OpenHour, OpenHourDetails> {

    override fun map(from: OpenHour): OpenHourDetails =
            OpenHourDetails(
                    from.id,

                    timeToString(from.mondayStart),
                    timeToString(from.mondayEnd),

                    timeToString(from.tuesdayStart),
                    timeToString(from.tuesdayEnd),

                    timeToString(from.wednesdayStart),
                    timeToString(from.wednesdayEnd),

                    timeToString(from.thursdayStart),
                    timeToString(from.thursdayEnd),

                    timeToString(from.fridayStart),
                    timeToString(from.fridayEnd),

                    timeToString(from.saturdayStart),
                    timeToString(from.saturdayEnd),

                    timeToString(from.sundayStart),
                    timeToString(from.sundayEnd)
            )

}