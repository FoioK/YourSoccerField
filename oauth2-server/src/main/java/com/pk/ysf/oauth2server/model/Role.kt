package com.pk.ysf.oauth2server.model

import javax.persistence.*

@Entity
data class Role(

        @Id
        @GeneratedValue
        val id: Long?,

        @Column(nullable = false, length = 32)
        val name: String,

        @OneToMany(mappedBy = "role")
        val users: List<UserRole>,

        @OneToMany(mappedBy = "permission")
        val permissions: List<RolePermission>
) {
        constructor() : this (
                0,
                "",
                emptyList(),
                emptyList()
        )
}