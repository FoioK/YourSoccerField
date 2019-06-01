package com.pk.ysf.api.model.dto

import com.pk.ysf.api.validation.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

data class BookingInput(

        @field:Positive var userCode: String = "",

        @field:NotNull
        @field:Pattern(message = DATE_PATTERN_MESSAGE, regexp = DATE_PATTERN)
        val startDate: String = "",

        @field:NotNull
        @field:Pattern(message = TIME_PATTERN_MESSAGE, regexp = TIME_PATTERN)
        val executionTime: String = "",

        @field:NotBlank
        @field:Pattern(message = AMOUNT_PATTERN_MESSAGE, regexp = AMOUNT_PATTERN)
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

        var userCode: String = ""
        var startDate: String = ""
        var executionTime: String = ""
        var amount: String = ""
        var isPayed: Boolean = false
        var soccerField: Long = 0

        fun build() = BookingInput(this)
    }

}