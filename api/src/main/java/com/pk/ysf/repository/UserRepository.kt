package com.pk.ysf.repository

import com.pk.ysf.apimodels.model.UserEntity
import com.pk.ysf.apimodels.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<List<UserEntity>>

    fun findByNickname(nickname: String): Optional<List<UserEntity>>

    @Transactional
    @Query(value = findLastUserCodeQuery, nativeQuery = true)
    fun findLastUserCode(): Optional<BigInteger>


    @Query(value = findNextUserCode, nativeQuery = true)
    @Transactional
    fun findNextUserCode(): Optional<BigInteger>

    @Modifying
    @Transactional
    @Query(value = updateNextUserCode)
    fun updateNextUserCode(@Param("code") code: Long): Int

    @Modifying
    @Transactional
    @Query(value = insertNextUserCodeQuery, nativeQuery = true)
    fun insertNextUserCode(@Param("code") code: Long): Int


    @Suppress("SpringDataRepositoryMethodReturnTypeInspection")
    @Query(value = findUserRoleByUserCodeQuery)
    fun findUserRoleByUserCode(@Param("userCode") userCode: Long): Optional<UserRole>

    companion object {

        const val findLastUserCodeQuery = "SELECT u.code FROM user u ORDER BY u.code DESC LIMIT 1"


        const val findNextUserCode = "SELECT next_code FROM user_code WHERE id = 1"

        const val updateNextUserCode = "UPDATE UserCode uc SET next_code = :code WHERE id = 1"

        const val insertNextUserCodeQuery = "INSERT INTO user_code (id, next_code) VALUES (1, :code)"


        const val findUserRoleByUserCodeQuery = "SELECT ur FROM UserRole ur " +
                "WHERE ur.userCode = :userCode"
    }
}