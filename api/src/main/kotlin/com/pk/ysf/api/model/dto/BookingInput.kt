package com.pk.ysf.api.model.dto

import com.pk.ysf.api.validation.AMOUNT_PATTERN
import com.pk.ysf.api.validation.DATE_PATTERN
import com.pk.ysf.api.validation.TIME_PATTERN
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

data class BookingInput(

        @field:Positive var userCode: Long = 0,

        @field:NotNull
        @field:Pattern(
                message = "invalid format",
                regexp = DATE_PATTERN
        )
        val startDate: String = "",

        @field:NotNull
        @field:Pattern(
                message = "must match with HH:MM pattern",
                regexp = TIME_PATTERN
        )
        val executionTime: String = "",

        @field:NotBlank
        @field:Pattern(
                message = "invalid format",
                regexp = AMOUNT_PATTERN
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