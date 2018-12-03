package com.pk.YourSoccerField.repository

import com.pk.YourSoccerField.model.SoccerField
import com.pk.YourSoccerField.model.Surface
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SoccerFieldRepository : JpaRepository<SoccerField, Long> {

    @Query(value = findSurfaceByIdQuery)
    fun findSurfaceById(surfaceId: Long): Optional<Surface>

    companion object {

        const val findSurfaceByIdQuery = "SELECT s FROM Surface s " +
                "WHERE s.id = ?1"
    }
}