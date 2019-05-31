package com.pk.ysf.api.model.dto

data class AddressDetails(

        val id: Long,
        val city: String,
        val street: String,
        val apartmentNumber: String

) {

    private constructor(builder: Builder) : this(
            builder.id,
            builder.city,
            builder.street,
            builder.apartmentNumber
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var city: String = ""
        var street: String = ""
        var apartmentNumber: String = ""

        fun build() = AddressDetails(this)
    }
}