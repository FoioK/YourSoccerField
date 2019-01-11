package com.pk.ysf.apimodels.model

import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@Entity
data class Booking(

        @Id
        @GeneratedValue
        val id: Long?,

        val userCode: Long,

        val startDate: LocalDateTime?,

        @Column(nullable = false)
        val executionTime: LocalTime?,

        @ManyToOne
        val soccerField: SoccerField?,

        val isPayed: Boolean?
) {
        constructor() : this (
                0,
                0,
                null,
                null,
                null,
                false
        )
}