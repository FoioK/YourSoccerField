package com.pk.ysf.api.controller

import com.pk.ysf.api.model.dto.LoginResponse
import com.pk.ysf.api.model.dto.UserInput
import com.pk.ysf.api.model.dto.UserReference
import com.pk.ysf.api.service.spec.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("\${spring.data.rest.base-path}/users")
class UserController @Autowired constructor(
        private val userService: UserService
) {

    @GetMapping
    fun login(
            @RequestParam(defaultValue = "") username: String,
            @RequestParam(defaultValue = "") password: String
    ): ResponseEntity<LoginResponse> =
            ResponseEntity.ok(userService.login(username, password))

    @PostMapping
    fun register(
            @RequestBody userInput: UserInput,
            request: HttpServletRequest
    ): ResponseEntity<UserReference> {
        val createdUser: UserReference = userService.create(userInput)

        return ResponseEntity
                .created(URI.create(request.requestURI + "/${createdUser.code}"))
                .body(userService.create(userInput))
    }

}