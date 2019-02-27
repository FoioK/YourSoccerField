package com.pk.ysf.apimodels.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

data class BookingInput(

        @field:Positive var userCode: Long = 0,

        @field:NotNull
        @field:Pattern(
                message = "invalid format",
                regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]"
        )
        val startDate: String = "",

        @field:NotNull
        @field:Pattern(
                message = "must match with HH:MM pattern",
                regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]\$"
        )
        val executionTime: String = "",

        @field:NotBlank
        @field:Pattern(
                message = "invalid format",
                regexp = "^(0|[1-9][0-9]*)(\\.[0-9]{2,2})?\$"
        )
        val amount: String = "",

        val isPayed: Boolean = false,

        @field:Positive val soccerField: Long = 0

) {

    private constructor(builder: Builder) : this(
            builder.userCode,
            builder.startDate,
            builder.executionTime,
            builder.amount,
            builder.isPayed,
            builder.soccerField
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        var userCode: Long = 0
        var startDate: String = ""
        var executionTime: String = ""
        var amount: String = ""
        var isPayed: Boolean = false
        var soccerField: Long = 0

        fun build() = BookingInput(this)
    }

}