package com.pk.ysf.api.service.mapper.surface

import com.pk.ysf.api.model.dto.SurfaceDetails
import com.pk.ysf.api.model.entity.Surface
import com.pk.ysf.api.service.mapper.BaseMapper
import org.springframework.stereotype.Component

@Component
class SurfaceToSurfaceDetails : BaseMapper<Surface, SurfaceDetails> {

    override fun map(from: Surface): SurfaceDetails = SurfaceDetails(from.id, from.name)

}