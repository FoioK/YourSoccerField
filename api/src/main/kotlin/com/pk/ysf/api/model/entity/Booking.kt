package com.pk.ysf.api.model.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@Entity
data class Booking(

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false)
        val userCode: String,

        @Column(nullable = false)
        val startDate: LocalDateTime,

        @Column(nullable = false)
        val executionTime: LocalTime,

        @Column(nullable = false, scale = 2)
        val amount: BigDecimal,

        @Column(nullable = false)
        val isPayed: Boolean,

        @ManyToOne
        val soccerField: SoccerField
) {

    private constructor(builder: Builder) : this(
            builder.id,
            builder.userCode,
            builder.startDate,
            builder.executionTime,
            builder.amount,
            builder.isPayed,
            builder.soccerField
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        var id: Long = 0
        var userCode: String = ""
        var startDate: LocalDateTime = LocalDateTime.now()
        var executionTime: LocalTime = LocalTime.now()
        var amount: BigDecimal = BigDecimal.ZERO
        var isPayed: Boolean = false
        var soccerField: SoccerField = SoccerField.build {  }

        fun build() = Booking(this)
    }
}