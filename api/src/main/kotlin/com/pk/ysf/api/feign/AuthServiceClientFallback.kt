package com.pk.ysf.api.feign

import com.pk.ysf.api.model.dto.AuthRegisterModel
import com.pk.ysf.api.model.dto.LoginResponse
import com.pk.ysf.api.model.dto.RegisterResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class AuthServiceClientFallback : AuthServiceClient {

    override fun login(
            authorization: String,
            username: String,
            password: String,
            grantType: String
    ): ResponseEntity<LoginResponse> {
        TODO("not implemented")
    }

    override fun register(
            authorization: String,
            authRegisterModel: AuthRegisterModel
    ): ResponseEntity<RegisterResponse> {
        TODO("not implemented")
    }

}