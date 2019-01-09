package com.pk.ysf.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserCode (

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false, insertable = false)
        val nextCode: Long
)