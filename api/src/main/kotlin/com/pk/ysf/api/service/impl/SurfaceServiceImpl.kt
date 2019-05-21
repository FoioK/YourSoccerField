package com.pk.ysf.api.service.impl

import com.pk.ysf.api.model.dto.SurfaceDetails
import com.pk.ysf.api.repository.SurfaceRepository
import com.pk.ysf.api.service.mapper.surface.SurfaceToSurfaceDetails
import com.pk.ysf.api.service.spec.SurfaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SurfaceServiceImpl @Autowired constructor(
        private val surfaceRepository: SurfaceRepository,
        private val surfaceToSurfaceDetails: SurfaceToSurfaceDetails
) : SurfaceService {

    override fun getAll(): List<SurfaceDetails> {
        val surfaces = surfaceRepository.findAll()

        return surfaceToSurfaceDetails.mapAll(surfaces).toList()
    }

}