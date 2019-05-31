package com.pk.ysf.api.model.dto

import com.pk.ysf.api.validation.TIME_PATTERN
import com.pk.ysf.api.validation.TIME_PATTERN_MESSAGE
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class OpenHourInput(


        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val mondayStart: String,

        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val mondayEnd: String,


        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val tuesdayStart: String,

        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val tuesdayEnd: String,


        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val wednesdayStart: String,

        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val wednesdayEnd: String,


        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val thursdayStart: String,

        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val thursdayEnd: String,


        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val fridayStart: String,

        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val fridayEnd: String,


        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val saturdayStart: String,

        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val saturdayEnd: String,


        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val sundayStart: String,

        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
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