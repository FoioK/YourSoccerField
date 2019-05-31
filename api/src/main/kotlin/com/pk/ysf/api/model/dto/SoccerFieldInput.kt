package com.pk.ysf.api.model.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class SoccerFieldInput(

        @field:NotBlank val name: String,
        @field:NotNull val address: AddressDTO,
        @field:Positive val surfaceId: Long,
        @field:Positive val width: Int,
        @field:Positive val length: Int,
        val price: String,
        @field:NotNull val isLighting: Boolean,
        @field:NotNull val isFenced: Boolean,
        @field:NotNull val isLockerRoom: Boolean,
        @field:NotNull val description: String,
        @field:NotNull val openHour: OpenHourInput

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