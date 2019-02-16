package com.pk.ysf.apimodels.entity

import java.time.LocalTime
import javax.persistence.*

@Entity
class OpenHour {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(nullable = false)
    var s1: LocalTime? = null
    @Column(nullable = false)
    var e1: LocalTime? = null

    @Column(nullable = false)
    var s2: LocalTime? = null
    @Column(nullable = false)
    var e2: LocalTime? = null

    @Column(nullable = false)
    var s3: LocalTime? = null
    @Column(nullable = false)
    var e3: LocalTime? = null

    @Column(nullable = false)
    var s4: LocalTime? = null
    @Column(nullable = false)
    var e4: LocalTime? = null

    @Column(nullable = false)
    var s5: LocalTime? = null
    @Column(nullable = false)
    var e5: LocalTime? = null

    @Column(nullable = false)
    var s6: LocalTime? = null
    @Column(nullable = false)
    var e6: LocalTime? = null

    @Column(nullable = false)
    var s7: LocalTime? = null
    @Column(nullable = false)
    var e7: LocalTime? = null

    @OneToMany(mappedBy = "openHour")
    var soccerField: List<SoccerField>? = null
}
