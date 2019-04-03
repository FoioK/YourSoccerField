package com.pk.ysf.apimodels.entity

import javax.persistence.*

@Entity
data class Surface(

        @Id
        @GeneratedValue
        val id: Long?,

        @Column(nullable = false, length = 64)
        val name: String,

        @OneToMany(mappedBy = "surface")
        val soccerFields: List<SoccerField>
) {

    constructor() : this(
            id = 0,
            name = "",
            soccerFields = emptyList()
    )

    private constructor(builder: Builder) : this(
            builder.id,
            builder.name,
            emptyList()
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var name: String = ""

        fun build() = Surface(this)
    }
}