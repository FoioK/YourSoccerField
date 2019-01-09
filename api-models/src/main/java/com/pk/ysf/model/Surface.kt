package com.pk.ysf.model

import javax.persistence.*

@Entity
data class Surface(

        @Id
        @GeneratedValue
        val id: Long?,

        @Column(nullable = false, length = 32)
        val name: String,

        @OneToMany(mappedBy = "surface")
        val SoccerFields: List<SoccerField>
) {
    constructor() : this(
            0,
            "",
            emptyList()
    )
}