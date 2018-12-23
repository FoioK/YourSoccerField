package com.pk.YourSoccerField.oauth2server.model

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
        val SoccerFields: List<SoccerField>
) {
    constructor() : this(
            0,
            "",
            "",
            "",
            emptyList(),
            emptyList()
    )
}