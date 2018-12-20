package com.pk.YourSoccerField.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class SoccerField(

        @Id
        @GeneratedValue
        val id: Long?,

        @Column(nullable = false, length = 64)
        val name: String?,

        @ManyToOne
        val address: Address?,

        @ManyToOne
        val surface: Surface?,

        @Column(nullable = false)
        val width: Int?,

        @Column(nullable = false)
        val length: Int?,

        @Column(scale = 2)
        val price: BigDecimal?,

        @Column(nullable = false)
        val isLighting: Boolean?,

        @Column(nullable = false)
        val isFenced: Boolean?,

        @Column(nullable = false)
        val isLockerRoom: Boolean?,

        val description: String?,

        @OneToMany(mappedBy = "soccerField")
        val bookingsId: List<Booking>,

        @OneToOne()
        val openHour: OpenHour?
) {
    constructor() : this(
            0,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            emptyList(),
            null
    )
}