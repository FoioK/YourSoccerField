package com.pk.ysf.apimodels.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class BookingInput(

        @Positive val userCode: Long = 0,
        @NotEmpty val startDate: String = "",
        @NotEmpty val executionTime: String = "",
        @NotEmpty val amount: String = "",
        @NotNull val isPayed: Boolean = false,
        @Positive val soccerField: Long = 0

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