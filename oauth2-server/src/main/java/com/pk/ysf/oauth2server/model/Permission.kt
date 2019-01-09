package com.pk.ysf.oauth2server.model

import javax.persistence.*

@Entity
data class Permission (

        @Id
        @GeneratedValue
        val id: Long,

        @Column(nullable = false, length = 64)
        val name: String,

        @OneToMany(mappedBy = "role")
        val roles: List<RolePermission>
)