package com.pk.YourSoccerField.config

import java.time.LocalDateTime
import java.util.*

class AccessTokenMapper {

    var id: Long? = null
    var code: Long? = null
    var authorities: List<String> = ArrayList()
    var email: String? = null
    var isActive: Boolean? = null
    var createTime: LocalDateTime? = null
}