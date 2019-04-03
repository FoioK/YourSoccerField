package com.pk.ysf.api.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter
import org.springframework.util.StringUtils

private const val EMAIL: String = "email"

class CustomUserAuthenticationConverter : UserAuthenticationConverter {

    override fun extractAuthentication(map: Map<String, *>): Authentication? =
            if (map.containsKey(UserAuthenticationConverter.USERNAME))
                UsernamePasswordAuthenticationToken(
                        this.getUserDetail(map), "N/A",
                        getAuthorities(map))
            else null

    private fun getUserDetail(map: Map<String, *>): User =
            User(map[EMAIL] as String, "", emptyList())

    private fun getAuthorities(map: Map<String, *>): Collection<GrantedAuthority>? {
        val authorities = map[UserAuthenticationConverter.AUTHORITIES]
        if (authorities is String)
            return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
        if (authorities is Collection<*>)
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString(authorities)
            )

        throw IllegalArgumentException("Authorities must be either a String or a Collection")
    }

    override fun convertUserAuthentication(userAuthentication: Authentication): MutableMap<String, *> {
        val response = mutableMapOf<String, Any>(
                UserAuthenticationConverter.USERNAME to userAuthentication.name
        )

        if (userAuthentication.authorities != null && !userAuthentication.authorities.isEmpty()) {
            response[UserAuthenticationConverter.AUTHORITIES] = AuthorityUtils.authorityListToSet(userAuthentication.authorities)
        }

        return response
    }
}