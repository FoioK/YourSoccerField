package com.pk.ysf.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class UserAddress(

        @Id
        @GeneratedValue
        val id: Long,

        val userCode: Long,

        @ManyToOne
        val address: Address
)