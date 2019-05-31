package com.pk.ysf.api.model.dto

import com.pk.ysf.service.dtoModel.AddressDTO

data class SoccerFieldInput(

        val name: String,
        val address: AddressDTO,
        val surfaceId: Long,
        val width: Int,
        val length: Int,
        val price: String,
        val isLighting: Boolean,
        val isFenced: Boolean,
        val isLockerRoom: Boolean,
        val description: String,
        val openHour: OpenHourInput

) {

    private constructor(builder: Builder) : this(
            builder.name,
            builder.address,
            builder.surfaceId,
            builder.width,
            builder.length,
            builder.price,
            builder.isLighting,
            builder.isFenced,
            builder.isLockerRoom,
            builder.description,
            builder.openHour
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var name: String = ""
        var address: AddressDTO = AddressDTO()
        var surfaceId: Long = 0
        var width: Int = 0
        var length: Int = 0
        var price: String = ""
        var isLighting: Boolean = false
        var isFenced: Boolean = false
        var isLockerRoom: Boolean = false
        var description: String = ""
        var openHour: OpenHourInput = OpenHourInput.build { }

        fun build() = SoccerFieldInput(this)
    }
}