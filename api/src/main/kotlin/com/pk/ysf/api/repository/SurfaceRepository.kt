package com.pk.ysf.api.repository

import com.pk.ysf.api.model.entity.Surface
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurfaceRepository : JpaRepository<Surface, Long>