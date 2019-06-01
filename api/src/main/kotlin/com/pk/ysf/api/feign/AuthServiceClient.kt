package com.pk.ysf.api.feign

import com.pk.ysf.api.model.dto.AuthRegisterModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@FeignClient(name = "auth-service", url = "\${feign.auth-service.address}")
interface AuthServiceClient {

    @PostMapping("/oauth/token")
    fun login(
            @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) contentType: String,

            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            @RequestParam("grant_type") grantType: String
    ): ResponseEntity<String>

    @PostMapping("/users")
    fun register(
            @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) contentType: String,

            @RequestBody authRegisterModel: AuthRegisterModel
    ): ResponseEntity<String>

}
