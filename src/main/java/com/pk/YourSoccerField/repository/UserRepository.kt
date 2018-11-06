package com.pk.YourSoccerField.repository

import com.pk.YourSoccerField.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.util.*

@Repository
interface UserRepository : PermissionRepository, JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<UserEntity>

    @Query(value = findNextUserCode, nativeQuery = true)
    @Transactional
    fun findNextUserCode(): Optional<BigInteger>

    @Modifying
    @Transactional
    @Query(value = updateNextUserCode)
    fun updateNextUserCode(@Param("code") code: Long): Int

    @Modifying
    @Transactional
    @Query(value = insertUserRole, nativeQuery = true)
    fun insertUserRole(
            @Param("userId") userId: Long,
            @Param("roleId") roleId: Long): Int

    companion object {

        const val findNextUserCode = "SELECT next_code FROM user_code WHERE id = 1"

        const val updateNextUserCode = "UPDATE UserCode uc SET next_code = :code WHERE id = 1"

        const val insertUserRole = "INSERT INTO user_role (user_id, role_id) " +
                "VALUES (:userId, :roleId)"
    }
}