package com.pk.ysf.api.service.spec

import com.pk.ysf.api.model.dto.SurfaceDetails
import org.springframework.stereotype.Service

@Service
interface SurfaceService {

    fun getAll(): List<SurfaceDetails>

}