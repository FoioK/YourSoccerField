package com.pk.YourSoccerField.repository

import com.pk.YourSoccerField.model.SoccerField
import com.pk.YourSoccerField.model.Surface
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SoccerFieldRepository : JpaRepository<SoccerField, Long> {

    @Query(value = findSurfaceByIdQuery)
    fun findSurfaceById(surfaceId: Long): Optional<Surface>

    @Query(value = findByAddressContainsQuery, nativeQuery = true)
    fun findByAddressContains(@Param("street") street: String): List<SoccerField>

    @Query(value = findExampleTenQuery, nativeQuery = true)
    fun findExampleTen(): List<SoccerField>

    companion object {

        const val findSurfaceByIdQuery = "SELECT s FROM Surface s " +
                "WHERE s.id = ?1"

        const val findByAddressContainsQuery = "SELECT * FROM soccer_field sf " +
                "JOIN address a ON a.id = sf.address_id " +
                "WHERE a.street LIKE CONCAT(:street, '%')"

        const val findExampleTenQuery = "SELECT * FROM soccer_field " +
                "ORDER BY RAND() LIMIT 10"
    }
}