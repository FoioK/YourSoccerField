package com.pk.ysf.api.model.dto

data class SoccerFieldDetails(

        val id: Long,
        val name: String,
        val address: AddressDetails,
        val surface: SurfaceDetails,
        val width: Int,
        val length: Int,
        val price: String,
        val isLighting: Boolean,
        val isFenced: Boolean,
        val isLockerRoom: Boolean,
        val description: String,
        val openHour: OpenHourDetails

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
            builder.openHour
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var name: String = ""
        var address: AddressDetails = AddressDetails.build { }
        var surface: SurfaceDetails = SurfaceDetails.build { }
        var width: Int = 0
        var length: Int = 0
        var price: String = ""
        var isLighting: Boolean = false
        var isFenced: Boolean = false
        var isLockerRoom: Boolean = false
        var description: String = ""
        var openHour: OpenHourDetails = OpenHourDetails.build {  }

        fun build() = SoccerFieldDetails(this)
    }
}