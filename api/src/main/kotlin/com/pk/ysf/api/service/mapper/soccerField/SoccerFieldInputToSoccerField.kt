package com.pk.ysf.api.service.mapper.soccerField

import com.pk.ysf.api.model.dto.SoccerFieldInput
import com.pk.ysf.api.model.entity.Address
import com.pk.ysf.api.model.entity.SoccerField
import com.pk.ysf.api.model.entity.Surface
import com.pk.ysf.api.model.exception.MissingEntityException
import com.pk.ysf.api.repository.SurfaceRepository
import com.pk.ysf.api.service.mapper.BaseMapper
import com.pk.ysf.api.service.mapper.openHour.OpenHourInputToOpenHour
import com.pk.ysf.service.dtoModel.AddressDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class SoccerFieldInputToSoccerField @Autowired constructor(
        private val surfaceRepository: SurfaceRepository,
        private val openHourInputToOpenHour: OpenHourInputToOpenHour
) : BaseMapper<SoccerFieldInput, SoccerField> {

    override fun map(from: SoccerFieldInput): SoccerField =
            SoccerField(
                    id = 0,
                    name = from.name,
                    address = mapAddress(from.address),
                    surface = mapSurface(from.surfaceId),
                    width = from.width,
                    length = from.length,
                    price = BigDecimal(from.price),
                    isLighting = from.isLighting,
                    isFenced = from.isFenced,
                    isLockerRoom = from.isLockerRoom,
                    description = from.description,
                    bookingsId = emptyList(),
                    openHour = openHourInputToOpenHour.map(from.openHour)
            )

    private fun mapAddress(from: AddressDTO): Address =
            Address(
                    from.id,
                    from.city,
                    from.street,
                    from.apartmentNumber,
                    Collections.emptyList(),
                    Collections.emptyList()
            )

    private fun mapSurface(surfaceId: Long): Surface =
            surfaceRepository.findById(surfaceId)
                    .orElseThrow { MissingEntityException("Cannot find surface with id $surfaceId") }

}