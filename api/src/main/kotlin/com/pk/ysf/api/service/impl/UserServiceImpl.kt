package com.pk.ysf.api.service.impl

import com.pk.ysf.api.annotation.ValidInput
import com.pk.ysf.api.feign.AuthServiceProvider
import com.pk.ysf.api.model.dto.LoginResponse
import com.pk.ysf.api.model.dto.UserInput
import com.pk.ysf.api.model.dto.UserReference
import com.pk.ysf.api.model.entity.User
import com.pk.ysf.api.model.exception.MissingEntityException
import com.pk.ysf.api.repository.UserRepository
import com.pk.ysf.api.service.spec.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
        private val authServiceProvider: AuthServiceProvider,
        private val userRepository: UserRepository
) : UserService {

    override fun login(username: String, password: String): LoginResponse {
        val user: User = userRepository
                .findByEmailOrUsernameAndActive(username, username, true)
                .orElseThrow { MissingEntityException("Cannot find user with username $username") }


    }

    override fun refreshSession(username: String, refreshToken: String): LoginResponse {
        val user: User = userRepository
                .findByEmailOrUsernameAndActive(username, username, true)
                .orElseThrow { MissingEntityException("Cannot find user with username $username") }


    }

    @ValidInput
    override fun create(userInput: UserInput): UserReference {
        TODO("not implemented")
    }
}