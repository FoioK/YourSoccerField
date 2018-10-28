package com.pk.YourSoccerField.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Booking(

        @Id
        @GeneratedValue
        val id: Long,

        @ManyToOne
        val user: UserEntity,

        val startDate: LocalDateTime,

        @Column(nullable = false)
        val executionTime: Int,

        @ManyToOne
        val soccerField: SoccerField,

        val isPayed: Boolean
)