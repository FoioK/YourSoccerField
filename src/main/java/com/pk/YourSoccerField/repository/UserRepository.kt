package com.pk.YourSoccerField.repository

import com.pk.YourSoccerField.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : PermissionRepository, JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<UserEntity>
}
