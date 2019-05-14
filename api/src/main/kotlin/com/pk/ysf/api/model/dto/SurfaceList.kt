package com.pk.ysf.api.model.dto

data class SurfaceList(

        val id: Long,
        val name: String

) {

    private constructor(builder: Builder) : this(
            builder.id,
            builder.name
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var name: String = ""

        fun build() = SurfaceList(this)
    }

}