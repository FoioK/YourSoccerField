package com.pk.ysf.api.feign

import com.pk.ysf.api.model.dto.AuthRegisterModel
import com.pk.ysf.api.model.dto.LoginResponse
import com.pk.ysf.api.model.dto.RegisterResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@FeignClient(name = "auth-service", url = "\${feign.auth-service.address}")
interface AuthServiceClient {

    @PostMapping("/oauth/token")
    fun login(
            @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            @RequestParam("grant_type") grantType: String
    ): ResponseEntity<LoginResponse>

    @PostMapping("/users")
    fun register(
            @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
            @RequestBody authRegisterModel: AuthRegisterModel
    ): ResponseEntity<RegisterResponse>

}