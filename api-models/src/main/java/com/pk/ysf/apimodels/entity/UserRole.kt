package com.pk.ysf.apimodels.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class UserRole(

        @Id
        @GeneratedValue
        val id: Long?,

        val userCode: Long,

        @ManyToOne
        val role: Role
)