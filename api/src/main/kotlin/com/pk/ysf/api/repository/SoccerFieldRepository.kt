package com.pk.ysf.api.repository

import com.pk.ysf.api.model.entity.SoccerField
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SoccerFieldRepository : JpaRepository<SoccerField, Long> {

    @Query(value = findExampleTenQuery, nativeQuery = true)
    fun findExampleTen(): List<SoccerField>

    @Query(value = findByAddressContainingQuery, nativeQuery = true)
    fun findByAddressContaining(@Param("value") value: String): List<SoccerField>

    companion object {

        const val findExampleTenQuery = "SELECT * FROM soccer_field " +
                "ORDER BY RAND() LIMIT 10"

        const val findByAddressContainingQuery =
                "Select * FROM soccer_field sf " +
                        "JOIN address a ON a.id = sf.address_id WHERE " +
                        "a.street LIKE %?1% OR a.city LIKE %?1%"
    }

}