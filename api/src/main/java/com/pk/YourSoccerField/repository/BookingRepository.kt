package com.pk.YourSoccerField.repository

import com.pk.YourSoccerField.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository : JpaRepository<Booking, Long> {

    @Query(value = findAllByDateQuery, nativeQuery = true)
    fun findAllByDate(
            @Param("soccerFieldId") soccerFieldId: Long,
            @Param("year") year: Int,
            @Param("month") month: Int,
            @Param("day") day: Int
    ): List<Booking>

    companion object {

        const val findAllByDateQuery = "SELECT * FROM Booking " +
                "WHERE soccer_field_id = :soccerFieldId AND " +
                "YEAR(start_date) = :year " +
                "AND MONTH(start_date) = :month " +
                "AND DAY(start_date) = :day"

    }
}