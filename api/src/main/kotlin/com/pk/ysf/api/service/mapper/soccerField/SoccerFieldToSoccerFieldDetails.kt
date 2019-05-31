package com.pk.ysf.api.service.mapper.soccerField

import com.pk.ysf.api.model.dto.SoccerFieldDetails
import com.pk.ysf.api.model.entity.SoccerField
import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.service.mapper.address.AddressToAddressDetails
import com.pk.ysf.api.service.mapper.openHour.OpenHourToOpenHourDetails
import com.pk.ysf.api.service.mapper.surface.SurfaceToSurfaceDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SoccerFieldToSoccerFieldDetails @Autowired constructor(
        private val addressToAddressDetails: AddressToAddressDetails,
        private val surfaceToSurfaceDetails: SurfaceToSurfaceDetails,
        private val openHourToOpenHourDetails: OpenHourToOpenHourDetails
) : BaseMapper<SoccerField, SoccerFieldDetails> {

    override fun map(from: SoccerField): SoccerFieldDetails =
            SoccerFieldDetails(
                    from.id,
                    from.name,
                    addressToAddressDetails.map(from.address),
                    surfaceToSurfaceDetails.map(from.surface),
                    from.width,
                    from.length,
                    from.price.toString(), // TODO potencial null
                    from.isLighting,
                    from.isFenced,
                    from.isLockerRoom,
                    from.description, // TODO potencial null
                    openHourToOpenHourDetails.map(from.openHour)
            )

}