package com.pk.ysf.api.feign

import com.pk.ysf.api.model.dto.LoginResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AuthServiceProvider @Autowired constructor(
        @Suppress("SpringJavaInjectionPointsAutowiringInspection")
        private val authServiceClient: AuthServiceClient
) {

    fun getUserAccess(
            username: String,
            accessKey: String,
            grantType: GrantType
    ): LoginResponse = if (grantType == GrantType.PASSWORD)
        getAccessByPassword(username, accessKey) else
        refreshAccess(username, accessKey)


    fun getAccessByPassword(username: String, password: String): LoginResponse {
        authServiceClient.login(
                authorization = "",
                username = username,
                password = password,
                grantType = GrantType.PASSWORD.name
        )
    }

    fun refreshAccess(username: String, refreshToken: String): LoginResponse {
        TODO()
    }
}