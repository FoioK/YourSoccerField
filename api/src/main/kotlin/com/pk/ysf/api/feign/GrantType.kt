package com.pk.ysf.api.feign

enum class GrantType(type: String) {

    PASSWORD("password"),
    REFRESH_TOKEN("refresh-token")

}