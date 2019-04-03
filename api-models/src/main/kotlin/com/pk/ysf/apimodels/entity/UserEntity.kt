package com.pk.ysf.apimodels.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
@SecondaryTable(name = "user_detail")
class UserEntity {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(nullable = false, updatable = false)
    var code: Long? = null

    @Column(nullable = false, length = 64)
    var email: String? = null

    @Column(nullable = false, length = 32, table = "user_detail")
    var firstName: String? = null

    @Column(nullable = false, length = 32, table = "user_detail")
    var secondName: String? = null

    @Column(nullable = false, length = 32, table = "user_detail")
    var nickname: String? = null

    @Column(nullable = false, table = "user_detail")
    var createTime: LocalDateTime? = null
}