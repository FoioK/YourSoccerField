package com.pk.ysf.api.repository

import com.pk.ysf.api.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmailOrUsernameAndActive(
            email: String,
            username: String,
            isActive: Boolean
    ): Optional<User>

}