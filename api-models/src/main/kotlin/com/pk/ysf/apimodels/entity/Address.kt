package com.pk.ysf.apimodels.entity

import java.util.Collections.emptyList
import javax.persistence.*

@Entity
data class Address(

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false, length = 64)
        val city: String,

        @Column(nullable = false, length = 64)
        val street: String,

        @Column(nullable = false, length = 32)
        val apartmentNumber: String,

        @OneToMany(mappedBy = "address")
        val users: List<UserAddress>,

        @OneToMany(mappedBy = "address")
        val soccerFields: List<SoccerField>
) {

    constructor() : this(
            id = 0,
            city = "",
            street = "",
            apartmentNumber = "",
            users = emptyList(),
            soccerFields = emptyList()
    )

    private constructor(builder: Builder) : this(
            builder.id,
            builder.city,
            builder.street,
            builder.apartmentNumber,
            emptyList(),
            emptyList()
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var city: String = ""
        var street: String = ""
        var apartmentNumber: String = ""

        fun build() = Address(this)
    }
}