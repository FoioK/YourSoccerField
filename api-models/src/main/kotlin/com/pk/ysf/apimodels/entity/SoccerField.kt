package com.pk.ysf.apimodels.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class SoccerField(

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false, length = 128)
        val name: String,

        @ManyToOne(cascade = [
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH]
        )
        val address: Address?,

        @ManyToOne(cascade = [
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH]
        )
        val surface: Surface?,

        @Column(nullable = false)
        val width: Int,

        @Column(nullable = false)
        val length: Int,

        @Column(nullable = false, scale = 2)
        val price: BigDecimal,

        @Column(nullable = false)
        val isLighting: Boolean,

        @Column(nullable = false)
        val isFenced: Boolean,

        @Column(nullable = false)
        val isLockerRoom: Boolean,

        val description: String,

        @OneToMany(mappedBy = "soccerField")
        val bookingsId: List<Booking>,

        @ManyToOne(cascade = [
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH]
        )
        val openHour: OpenHour?
) {

    private constructor(builder: Builder) : this(
            builder.id,
            builder.name,
            builder.address,
            builder.surface,
            builder.width,
            builder.length,
            builder.price,
            builder.isLighting,
            builder.isFenced,
            builder.isLockerRoom,
            builder.description,
            builder.bookingsId,
            builder.openHour
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var name: String = ""
        var address: Address? = null
        var surface: Surface? = null
        var width: Int = 0
        var length: Int = 0
        var price: BigDecimal = BigDecimal.ZERO
        var isLighting: Boolean = false
        var isFenced: Boolean = false
        var isLockerRoom: Boolean = false
        var description: String = ""
        var bookingsId: List<Booking> = emptyList()
        var openHour: OpenHour? = null

        fun build() = SoccerField(this)
    }
}