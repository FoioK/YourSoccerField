package com.pk.ysf.oauth2server.repository

import com.pk.ysf.apimodels.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : PermissionRepository, JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<List<UserEntity>>

}