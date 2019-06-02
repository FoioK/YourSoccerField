package com.pk.ysf.api.model.dto

import java.time.LocalDateTime

data class UserReference (

        val id: Long,
        val code: String,
        val email: String,
        val firstName: String,
        val secondName: String,
        val username: String,
        val createTime: LocalDateTime

) {

    private constructor(builder: Builder) : this(
            builder.id,
            builder.code,
            builder.email,
            builder.firstName,
            builder.secondName,
            builder.username,
            builder.createTime
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var code: String = ""
        var email: String = ""
        var firstName: String = ""
        var secondName: String = ""
        var username: String = ""
        var createTime: LocalDateTime = LocalDateTime.MIN

        fun build() = UserReference(this)
    }
}