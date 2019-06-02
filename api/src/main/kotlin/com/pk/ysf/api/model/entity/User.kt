package com.pk.ysf.api.model.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false, updatable = false)
        val code: String,

        @Column(nullable = false, length = 64)
        val email: String,

        @Column(nullable = false, length = 64)
        val firstName: String,

        @Column(nullable = false, length = 64)
        val secondName: String,

        @Column(nullable = false, length = 64)
        val username: String,

        @Column(nullable = false)
        val createTime: LocalDateTime,

        val isActive: Boolean

) {

    private constructor(builder: Builder) : this(
            builder.id,
            builder.code,
            builder.email,
            builder.firstName,
            builder.secondName,
            builder.username,
            builder.createTime,
            builder.isActive
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        val id: Long = 0
        val code: String = ""
        val email: String = ""
        val firstName: String = ""
        val secondName: String = ""
        val username: String = ""
        val createTime: LocalDateTime = LocalDateTime.MIN
        var isActive: Boolean = false

        fun build() = User(this)
    }
}