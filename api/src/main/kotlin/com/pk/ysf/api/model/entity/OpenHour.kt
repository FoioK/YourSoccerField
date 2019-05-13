package com.pk.ysf.api.model.entity

import java.time.LocalTime
import javax.persistence.*

@Entity
class OpenHour(

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false)
        val s1: LocalTime,
        @Column(nullable = false)
        val e1: LocalTime,

        @Column(nullable = false)
        val s2: LocalTime,
        @Column(nullable = false)
        val e2: LocalTime,

        @Column(nullable = false)
        val s3: LocalTime,
        @Column(nullable = false)
        val e3: LocalTime,

        @Column(nullable = false)
        val s4: LocalTime,
        @Column(nullable = false)
        val e4: LocalTime,

        @Column(nullable = false)
        val s5: LocalTime,
        @Column(nullable = false)
        val e5: LocalTime,

        @Column(nullable = false)
        val s6: LocalTime,
        @Column(nullable = false)
        val e6: LocalTime,

        @Column(nullable = false)
        val s7: LocalTime,
        @Column(nullable = false)
        val e7: LocalTime,

        @OneToMany(mappedBy = "openHour")
        val soccerField: List<SoccerField>
) {

    constructor() : this(
            id = 0,
            s1 = LocalTime.MIN,
            e1 = LocalTime.MIN,
            s2 = LocalTime.MIN,
            e2 = LocalTime.MIN,
            s3 = LocalTime.MIN,
            e3 = LocalTime.MIN,
            s4 = LocalTime.MIN,
            e4 = LocalTime.MIN,
            s5 = LocalTime.MIN,
            e5 = LocalTime.MIN,
            s6 = LocalTime.MIN,
            e6 = LocalTime.MIN,
            s7 = LocalTime.MIN,
            e7 = LocalTime.MIN,
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
