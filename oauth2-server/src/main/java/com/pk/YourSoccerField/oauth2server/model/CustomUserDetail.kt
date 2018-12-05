package com.pk.YourSoccerField.oauth2server.model

import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime

class CustomUserDetail(user: UserEntity) :
        User(user.email, user.password, user.grantedAuthorityList) {

    var id: Long? = null
    val code: Long?
    var email: String? = null
    val isActive: Boolean
    var createTime: LocalDateTime? = null

    init {
        this.id = user.id
        this.code = user.code
        this.email = user.email
        this.isActive = user.isActive
        this.createTime = user.createTime
    }
}