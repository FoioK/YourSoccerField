package com.pk.ysf.api.repository

import com.pk.ysf.apimodels.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Optional<Role>
}