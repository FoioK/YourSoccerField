package com.pk.ysf.api.model.dto

data class OpenHourInput (

        val mondayStart: String,
        val mondayEnd: String,

        val tuesdayStart: String,
        val tuesdayEnd: String,

        val wednesdayStart: String,
        val wednesdayEnd: String,

        val thursdayStart: String,
        val thursdayEnd: String,

        val fridayStart: String,
        val fridayEnd: String,

        val saturdayStart: String,
        val saturdayEnd: String,

        val sundayStart: String,
        val sundayEnd: String

) {

    private constructor(builder: Builder) : this(
            builder.mondayStart,
            builder.mondayEnd,

            builder.tuesdayStart,
            builder.tuesdayEnd,

            builder.wednesdayStart,
            builder.wednesdayEnd,

            builder.thursdayStart,
            builder.thursdayEnd,

            builder.fridayStart,
            builder.fridayEnd,

            builder.saturdayStart,
            builder.saturdayEnd,

            builder.sundayStart,
            builder.sundayEnd
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var mondayStart: String = ""
        var mondayEnd: String = ""

        var tuesdayStart: String = ""
        var tuesdayEnd: String = ""

        var wednesdayStart: String = ""
        var wednesdayEnd: String = ""

        var thursdayStart: String = ""
        var thursdayEnd: String = ""

        var fridayStart: String = ""
        var fridayEnd: String = ""

        var saturdayStart: String = ""
        var saturdayEnd: String = ""

        var sundayStart: String = ""
        var sundayEnd: String = ""

        fun build() = OpenHourInput(this)
    }
}