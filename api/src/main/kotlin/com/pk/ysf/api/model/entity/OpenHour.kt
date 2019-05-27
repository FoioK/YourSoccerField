package com.pk.ysf.api.model.entity

import java.time.LocalTime
import javax.persistence.*

@Entity
class OpenHour(

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false)
        val mondayStart: LocalTime,
        @Column(nullable = false)
        val mondayEnd: LocalTime,

        @Column(nullable = false)
        val tuesdayStart: LocalTime,
        @Column(nullable = false)
        val tuesdayEnd: LocalTime,

        @Column(nullable = false)
        val wednesdayStart: LocalTime,
        @Column(nullable = false)
        val wednesdayEnd: LocalTime,

        @Column(nullable = false)
        val thursdayStart: LocalTime,
        @Column(nullable = false)
        val thursdayEnd: LocalTime,

        @Column(nullable = false)
        val fridayStart: LocalTime,
        @Column(nullable = false)
        val fridayEnd: LocalTime,

        @Column(nullable = false)
        val saturdayStart: LocalTime,
        @Column(nullable = false)
        val saturdayEnd: LocalTime,

        @Column(nullable = false)
        val sundayStart: LocalTime,
        @Column(nullable = false)
        val sundayEnd: LocalTime,

        @OneToMany(mappedBy = "openHour")
        val soccerField: List<SoccerField>
) {

    constructor() : this(
            id = 0,
            mondayStart = LocalTime.MIN,
            mondayEnd = LocalTime.MIN,
            tuesdayStart = LocalTime.MIN,
            tuesdayEnd = LocalTime.MIN,
            wednesdayStart = LocalTime.MIN,
            wednesdayEnd = LocalTime.MIN,
            thursdayStart = LocalTime.MIN,
            thursdayEnd = LocalTime.MIN,
            fridayStart = LocalTime.MIN,
            fridayEnd = LocalTime.MIN,
            saturdayStart = LocalTime.MIN,
            saturdayEnd = LocalTime.MIN,
            sundayStart = LocalTime.MIN,
            sundayEnd = LocalTime.MIN,
            soccerField = emptyList()
    )

    private constructor(builder: Builder) : this(
            builder.id,
            builder.s1,
            builder.e1,
            builder.s2,
            builder.e2,
            builder.s3,
            builder.e3,
            builder.s4,
            builder.e4,
            builder.s5,
            builder.e5,
            builder.s6,
            builder.e6,
            builder.s7,
            builder.e7,
            emptyList()
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0

        var s1: LocalTime = LocalTime.MIN
        var e1: LocalTime = LocalTime.MIN

        var s2: LocalTime = LocalTime.MIN
        var e2: LocalTime = LocalTime.MIN

        var s3: LocalTime = LocalTime.MIN
        var e3: LocalTime = LocalTime.MIN

        var s4: LocalTime = LocalTime.MIN
        var e4: LocalTime = LocalTime.MIN

        var s5: LocalTime = LocalTime.MIN
        var e5: LocalTime = LocalTime.MIN

        var s6: LocalTime = LocalTime.MIN
        var e6: LocalTime = LocalTime.MIN

        var s7: LocalTime = LocalTime.MIN
        var e7: LocalTime = LocalTime.MIN

        fun build() = OpenHour(this)
    }
}
