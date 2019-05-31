package com.pk.ysf.api.service.mapper.openHour

import com.pk.ysf.api.model.dto.OpenHourInput
import com.pk.ysf.api.model.entity.OpenHour
import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.util.stringToTime
import org.springframework.stereotype.Component

@Component
class OpenHourInputToOpenHour : BaseMapper<OpenHourInput, OpenHour> {

    override fun map(from: OpenHourInput): OpenHour =
            OpenHour(
                    0,
                    stringToTime(from.mondayStart),
                    stringToTime(from.mondayEnd),

                    stringToTime(from.tuesdayStart),
                    stringToTime(from.tuesdayEnd),

                    stringToTime(from.wednesdayStart),
                    stringToTime(from.wednesdayEnd),

                    stringToTime(from.thursdayStart),
                    stringToTime(from.thursdayEnd),

                    stringToTime(from.fridayStart),
                    stringToTime(from.fridayEnd),

                    stringToTime(from.saturdayStart),
                    stringToTime(from.saturdayEnd),

                    stringToTime(from.sundayStart),
                    stringToTime(from.sundayEnd),

                    emptyList()
            )

}