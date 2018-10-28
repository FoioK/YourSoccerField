package com.pk.YourSoccerField.model

import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user")
@SecondaryTable(name = "user_detail")
class UserEntity {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(nullable = false, updatable = false, unique = true)
    var code: Long? = null

    @Column(nullable = false, length = 64)
    var email: String? = null

    @Column(nullable = false, length = 64)
    var password: String? = null

    @Column(nullable = false)
    var isActive: Boolean = false

    @Column(nullable = false, length = 32, table = "user_detail")
    var firstName: String? = null

    @Column(nullable = false, length = 32, table = "user_detail")
    var secondName: String? = null

    @Column(nullable = false, length = 32, table = "user_detail")
    var nickName: String? = null

    @Column(nullable = false, table = "user_detail")
    var createTime: LocalDateTime? = null

    @OneToMany(mappedBy = "user")
    var roles: Set<UserRole>? = null

    @OneToMany(mappedBy = "user")
    var addresses: List<UserAddress>? = null

    @OneToMany(mappedBy = "user")
    var bookings: List<Booking>? = null

    @Transient
    var grantedAuthorityList: Collection<GrantedAuthority> = ArrayList()
}