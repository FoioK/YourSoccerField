package com.pk.ysf.apimodels.dto

data class BookingInput(

        val userCode: Long,
        val startDate: String,
        val executionTime: String,
        val amount: String,
        val isPayed: Boolean,
        val soccerField: Long

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