package com.pk.ysf.api.model.dto

data class LoginResponse(

        val access_token: String,
        val token_type: String,
        val refresh_token: String,
        val expires_in: Long,
        val code: String,
        val nickname: String

)