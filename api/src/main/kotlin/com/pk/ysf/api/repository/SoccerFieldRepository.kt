package com.pk.ysf.api.repository

import com.pk.ysf.api.model.entity.OpenHour
import com.pk.ysf.api.model.entity.SoccerField
import com.pk.ysf.api.model.entity.Surface
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SoccerFieldRepository : JpaRepository<SoccerField, Long> {

    @Query(value = findByAddressContainingQuery, nativeQuery = true)
    fun findByAddressContaining(@Param("value") value: String): List<SoccerField>

    @Query(value = findExampleTenQuery, nativeQuery = true)
    fun findExampleTen(): List<SoccerField>

    @Query(value = findByCustomCriteriaQuery, nativeQuery = true)
    fun findByCustomCriteria(whereClause: String): List<SoccerField>


    @Query(value = findAllSurfaceQuery)
    fun findAllSurface(): MutableList<Surface>

    @Query(value = findSurfaceByIdQuery)
    fun findSurfaceById(surfaceId: Long): Optional<Surface>

    @Query(value = findSurfaceByIdInQuery)
    fun findSurfaceByIdIn(@Param("ids") surfaces: List<Long>): List<Surface>


    @Query(value = findOpenHourByIdQuery)
    fun findOpenHourById(openHourId: Long): Optional<OpenHour>

    companion object {

        const val findByAddressContainingQuery =
                "Select * FROM soccer_field sf " +
                        "JOIN address a ON a.id = sf.address_id WHERE " +
                        "a.street LIKE %?1% OR a.city LIKE %?1%"

        const val findExampleTenQuery = "SELECT * FROM soccer_field " +
                "ORDER BY RAND() LIMIT 10"

        const val findByCustomCriteriaQuery = "SELECT * FROM soccer_field sf " +
                "WHERE :whereClause"


        const val findAllSurfaceQuery = "SELECT s FROM Surface s"

        const val findSurfaceByIdQuery = "SELECT s FROM Surface s " +
                "WHERE s.id = ?1"

        const val findSurfaceByIdInQuery = "SELECT s FROM Surface s " +
                "WHERE s.id IN :ids"


        const val findOpenHourByIdQuery = "SELECT o FROM OpenHour o " +
                "WHERE o.id = ?1"
    }
}