package com.pk.ysf.api.model.dto

data class BookingDetails(

        val id: Long,
        val userCode: String,
        val startDate: String,
        val executionTime: String,
        val amount: String,
        val isPayed: Boolean,
        val soccerField: Long

) {

    private constructor(builder: Builder) : this(
            builder.id,
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

        var id: Long = 0
        var userCode: String = ""
        var startDate: String = ""
        var executionTime: String = ""
        var amount: String = ""
        var isPayed: Boolean = false
        var soccerField: Long = 0

        fun build() = BookingDetails(this)
    }

}