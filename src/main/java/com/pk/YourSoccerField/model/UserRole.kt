package com.pk.YourSoccerField.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class UserRole(

        @Id
        @GeneratedValue
        val id: Long,

        @ManyToOne
        val user: User,

        @ManyToOne
        val role: Role
)