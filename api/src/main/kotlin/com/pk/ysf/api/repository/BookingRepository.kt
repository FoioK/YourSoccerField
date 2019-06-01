package com.pk.ysf.api.repository

import com.pk.ysf.api.model.entity.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface BookingRepository : JpaRepository<Booking, Long> {

    fun findAllByUserCode(userCode: String): List<Booking>

    @Query(value = findAllByDateQuery, nativeQuery = true)
    fun findAllByDate(
            @Param("soccerFieldId") soccerFieldId: Long,
            @Param("year") year: Int = currentDate.year,
            @Param("month") month: Int = currentDate.monthValue,
            @Param("day") day: Int = currentDate.dayOfMonth
    ): List<Booking>

    @Query(value = findAllBySoccerFieldIdQuery, nativeQuery = true)
    fun findAllBySoccerField(@Param("soccerFieldId") soccerFieldId: Long): List<Booking>

    companion object {

        const val findAllByDateQuery = "SELECT * FROM Booking " +
                "WHERE soccer_field_id = :soccerFieldId AND " +
                "YEAR(start_date) = :year " +
                "AND MONTH(start_date) = :month " +
                "AND DAY(start_date) = :day"

        val currentDate: LocalDate = LocalDate.now()

        const val findAllBySoccerFieldIdQuery = "SELECT * FROM Booking " +
                "WHERE soccer_field_id = :soccerFieldId"

    }
}