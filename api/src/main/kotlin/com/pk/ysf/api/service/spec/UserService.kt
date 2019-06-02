package com.pk.ysf.api.service.spec

import com.pk.ysf.api.model.dto.LoginResponse
import com.pk.ysf.api.model.dto.UserInput
import com.pk.ysf.api.model.dto.UserReference

interface UserService {

    fun login(username: String, password: String): LoginResponse

    fun refreshSession(username: String, refreshToken: String): LoginResponse

    fun create(userInput: UserInput): UserReference

}