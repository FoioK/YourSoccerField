package com.pk.YourSoccerField.oauth2server.repository

import com.pk.YourSoccerField.oauth2server.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : PermissionRepository, JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<List<UserEntity>>

}