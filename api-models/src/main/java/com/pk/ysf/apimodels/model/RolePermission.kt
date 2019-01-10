package com.pk.ysf.apimodels.model

import com.pk.ysf.apimodels.model.Role
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class RolePermission(

        @Id
        @GeneratedValue
        val id: Long,

        @ManyToOne
        val role: Role,

        @ManyToOne
        val permission: Permission
)