package com.pk.ysf.api.feign

import com.pk.ysf.api.model.dto.AuthRegisterModel
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class AuthServiceClientFallback : AuthServiceClient {

    override fun login(authorization: String, contentType: String, username: String, password: String, grantType: String): ResponseEntity<String> {
        TODO("not implemented")
    }

    override fun register(authorization: String, contentType: String, authRegisterModel: AuthRegisterModel): ResponseEntity<String> {
        TODO("not implemented")
    }

}