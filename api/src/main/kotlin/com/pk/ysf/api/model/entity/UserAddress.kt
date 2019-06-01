package com.pk.ysf.api.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class UserAddress(

        @Id
        @GeneratedValue
        val id: Long,

        val userCode: String,

        @ManyToOne
        val address: Address
)