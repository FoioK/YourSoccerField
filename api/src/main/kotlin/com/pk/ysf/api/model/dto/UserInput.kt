package com.pk.ysf.api.model.dto

data class UserInput(


        val email: String,
        val firstName: String,
        val secondName: String,
        val username: String

) {

    private constructor(builder: Builder) : this(
            builder.email,
            builder.firstName,
            builder.secondName,
            builder.username
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var email: String = ""
        var firstName: String = ""
        var secondName: String = ""
        var username: String = ""

        fun build() = UserInput(this)
    }

}