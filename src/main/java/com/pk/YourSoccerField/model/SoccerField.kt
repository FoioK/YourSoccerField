package com.pk.YourSoccerField.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class SoccerField(

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false, length = 32)
        val name: String,

        @ManyToOne
        val address: Address?,

        @ManyToOne
        val surface: Surface?,

        val width: Int,

        val length: Int,

        @Column(scale = 2)
        val price: BigDecimal?,

        val isLighting: Boolean,

        val isFenced: Boolean,

        val isLockerRoom: Boolean,

        val description: String,

        @OneToMany(mappedBy = "soccerField")
        val bookingsId: List<Booking>
)