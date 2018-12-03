package com.pk.YourSoccerField.oauth2server.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository {

    companion object {

        const val getPermissionQuery = "SELECT p.name FROM user u " +
                "JOIN user_role ur ON u.code = ur.user_code " +
                "JOIN role r ON ur.role_id = r.id " +
                "JOIN role_permission rp ON r.id = rp.role_id " +
                "JOIN permission p ON rp.permission_id = p.id " +
                "WHERE u.email like ?1"

    }

    @Query(value = getPermissionQuery, nativeQuery = true)
    fun getPermissions(email: String): List<String>
}
